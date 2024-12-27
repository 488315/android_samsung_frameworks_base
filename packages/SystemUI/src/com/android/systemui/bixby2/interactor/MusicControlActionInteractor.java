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

    public final class Companion {
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
    /* JADX WARN: Removed duplicated region for block: B:12:0x0093 A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:15:0x005d A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0073 A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00ab A[ORIG_RETURN, RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00a9 A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:5:0x009f A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x007e A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final int getStreamTypeFromString(android.content.Context r2, java.lang.String r3) {
        /*
            r1 = this;
            int r0 = r3.hashCode()
            switch(r0) {
                case -1961507235: goto La1;
                case -1853328508: goto L96;
                case -1719581065: goto L8a;
                case -1477758307: goto L80;
                case -711551941: goto L75;
                case -682366098: goto L6a;
                case -610069815: goto L60;
                case -84029337: goto L53;
                case 437139768: goto L42;
                case 988376414: goto L34;
                case 1232193329: goto L2a;
                case 1422875489: goto L1f;
                case 1432896029: goto L14;
                case 1660806934: goto L9;
                default: goto L7;
            }
        L7:
            goto La9
        L9:
            java.lang.String r1 = "volume_control_system"
            boolean r1 = r3.equals(r1)
            if (r1 != 0) goto L9f
            goto La9
        L14:
            java.lang.String r1 = "volume_control_media"
            boolean r1 = r3.equals(r1)
            if (r1 != 0) goto L7e
            goto La9
        L1f:
            java.lang.String r1 = "volume_control_bixby"
            boolean r1 = r3.equals(r1)
            if (r1 != 0) goto L93
            goto La9
        L2a:
            java.lang.String r1 = "mute_bluetooth_volume"
            boolean r1 = r3.equals(r1)
            if (r1 != 0) goto L5d
            goto La9
        L34:
            java.lang.String r1 = "mute_all_volume"
            boolean r1 = r3.equals(r1)
            if (r1 != 0) goto L3e
            goto La9
        L3e:
            r1 = 31
            goto Lac
        L42:
            java.lang.String r0 = "volume_control"
            boolean r3 = r3.equals(r0)
            if (r3 != 0) goto L4c
            goto La9
        L4c:
            com.android.systemui.bixby2.util.AudioManagerWrapper r1 = r1.audioManagerWrapper
            int r1 = r1.getAdjustedStreamType(r2)
            goto Lac
        L53:
            java.lang.String r1 = "volume_control_bluetooth"
            boolean r1 = r3.equals(r1)
            if (r1 != 0) goto L5d
            goto La9
        L5d:
            r1 = 30
            goto Lac
        L60:
            java.lang.String r1 = "volume_control_ringtone"
            boolean r1 = r3.equals(r1)
            if (r1 != 0) goto L73
            goto La9
        L6a:
            java.lang.String r1 = "mute_ringtones_volume"
            boolean r1 = r3.equals(r1)
            if (r1 != 0) goto L73
            goto La9
        L73:
            r1 = 2
            goto Lac
        L75:
            java.lang.String r1 = "mute_media_volume"
            boolean r1 = r3.equals(r1)
            if (r1 != 0) goto L7e
            goto La9
        L7e:
            r1 = 3
            goto Lac
        L80:
            java.lang.String r1 = "volume_control_noti"
            boolean r1 = r3.equals(r1)
            if (r1 != 0) goto Lab
            goto La9
        L8a:
            java.lang.String r1 = "mute_bixby_volume"
            boolean r1 = r3.equals(r1)
            if (r1 != 0) goto L93
            goto La9
        L93:
            r1 = 11
            goto Lac
        L96:
            java.lang.String r1 = "mute_system_volume"
            boolean r1 = r3.equals(r1)
            if (r1 != 0) goto L9f
            goto La9
        L9f:
            r1 = 1
            goto Lac
        La1:
            java.lang.String r1 = "mute_noti_volume"
            boolean r1 = r3.equals(r1)
            if (r1 != 0) goto Lab
        La9:
            r1 = -1
            goto Lac
        Lab:
            r1 = 5
        Lac:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.bixby2.interactor.MusicControlActionInteractor.getStreamTypeFromString(android.content.Context, java.lang.String):int");
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
            if (!str.equals("control_music")) {
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
            ((CommandProvider.AnonymousClass1) iCommandActionCallback).onActionFinished(mute.responseCode, mute.responseMessage);
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
        if (!str.equals("control_music")) {
            return null;
        }
        Command.StatefulBuilder statefulBuilder3 = new Command.StatefulBuilder(str2);
        statefulBuilder3.mStatus = 1;
        statefulBuilder3.mTemplate = new MediaControlTemplate(1, 1023, "");
        return statefulBuilder3.build();
    }
}
