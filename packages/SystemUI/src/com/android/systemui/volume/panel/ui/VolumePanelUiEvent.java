package com.android.systemui.volume.panel.ui;

import com.android.internal.logging.UiEventLogger;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class VolumePanelUiEvent implements UiEventLogger.UiEventEnum {
    public static final /* synthetic */ VolumePanelUiEvent[] $VALUES;
    public static final VolumePanelUiEvent VOLUME_PANEL_ALARM_SLIDER_TOUCHED;
    public static final VolumePanelUiEvent VOLUME_PANEL_ANC_POPUP_SHOWN;
    public static final VolumePanelUiEvent VOLUME_PANEL_AUDIO_MODE_CHANGE_TO_CALLING;
    public static final VolumePanelUiEvent VOLUME_PANEL_AUDIO_MODE_CHANGE_TO_NORMAL;
    public static final VolumePanelUiEvent VOLUME_PANEL_AUDIO_SHARING_SLIDER_TOUCHED;
    public static final VolumePanelUiEvent VOLUME_PANEL_GONE;
    public static final VolumePanelUiEvent VOLUME_PANEL_LIVE_CAPTION_TOGGLE_CLICKED;
    public static final VolumePanelUiEvent VOLUME_PANEL_LIVE_CAPTION_TOGGLE_GONE;
    public static final VolumePanelUiEvent VOLUME_PANEL_LIVE_CAPTION_TOGGLE_SHOWN;
    public static final VolumePanelUiEvent VOLUME_PANEL_MEDIA_OUTPUT_CLICKED;
    public static final VolumePanelUiEvent VOLUME_PANEL_MUSIC_SLIDER_TOUCHED;
    public static final VolumePanelUiEvent VOLUME_PANEL_NOTIFICATION_SLIDER_TOUCHED;
    public static final VolumePanelUiEvent VOLUME_PANEL_RING_SLIDER_TOUCHED;
    public static final VolumePanelUiEvent VOLUME_PANEL_SHOWN;
    public static final VolumePanelUiEvent VOLUME_PANEL_SOUND_SETTINGS_CLICKED;
    public static final VolumePanelUiEvent VOLUME_PANEL_SPATIAL_AUDIO_POP_UP_SHOWN;
    public static final VolumePanelUiEvent VOLUME_PANEL_SPATIAL_AUDIO_TOGGLE_CLICKED;
    public static final VolumePanelUiEvent VOLUME_PANEL_VOICE_CALL_SLIDER_TOUCHED;
    private final int metricId;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        VolumePanelUiEvent volumePanelUiEvent = new VolumePanelUiEvent("VOLUME_PANEL_SHOWN", 0, 1634);
        VOLUME_PANEL_SHOWN = volumePanelUiEvent;
        VolumePanelUiEvent volumePanelUiEvent2 = new VolumePanelUiEvent("VOLUME_PANEL_GONE", 1, 1635);
        VOLUME_PANEL_GONE = volumePanelUiEvent2;
        VolumePanelUiEvent volumePanelUiEvent3 = new VolumePanelUiEvent("VOLUME_PANEL_MEDIA_OUTPUT_CLICKED", 2, 1636);
        VOLUME_PANEL_MEDIA_OUTPUT_CLICKED = volumePanelUiEvent3;
        VolumePanelUiEvent volumePanelUiEvent4 = new VolumePanelUiEvent("VOLUME_PANEL_AUDIO_MODE_CHANGE_TO_NORMAL", 3, 1680);
        VOLUME_PANEL_AUDIO_MODE_CHANGE_TO_NORMAL = volumePanelUiEvent4;
        VolumePanelUiEvent volumePanelUiEvent5 = new VolumePanelUiEvent("VOLUME_PANEL_AUDIO_MODE_CHANGE_TO_CALLING", 4, 1681);
        VOLUME_PANEL_AUDIO_MODE_CHANGE_TO_CALLING = volumePanelUiEvent5;
        VolumePanelUiEvent volumePanelUiEvent6 = new VolumePanelUiEvent("VOLUME_PANEL_SOUND_SETTINGS_CLICKED", 5, 1638);
        VOLUME_PANEL_SOUND_SETTINGS_CLICKED = volumePanelUiEvent6;
        VolumePanelUiEvent volumePanelUiEvent7 = new VolumePanelUiEvent("VOLUME_PANEL_MUSIC_SLIDER_TOUCHED", 6, 1639);
        VOLUME_PANEL_MUSIC_SLIDER_TOUCHED = volumePanelUiEvent7;
        VolumePanelUiEvent volumePanelUiEvent8 = new VolumePanelUiEvent("VOLUME_PANEL_VOICE_CALL_SLIDER_TOUCHED", 7, 1640);
        VOLUME_PANEL_VOICE_CALL_SLIDER_TOUCHED = volumePanelUiEvent8;
        VolumePanelUiEvent volumePanelUiEvent9 = new VolumePanelUiEvent("VOLUME_PANEL_RING_SLIDER_TOUCHED", 8, 1641);
        VOLUME_PANEL_RING_SLIDER_TOUCHED = volumePanelUiEvent9;
        VolumePanelUiEvent volumePanelUiEvent10 = new VolumePanelUiEvent("VOLUME_PANEL_NOTIFICATION_SLIDER_TOUCHED", 9, 1642);
        VOLUME_PANEL_NOTIFICATION_SLIDER_TOUCHED = volumePanelUiEvent10;
        VolumePanelUiEvent volumePanelUiEvent11 = new VolumePanelUiEvent("VOLUME_PANEL_ALARM_SLIDER_TOUCHED", 10, 1643);
        VOLUME_PANEL_ALARM_SLIDER_TOUCHED = volumePanelUiEvent11;
        VolumePanelUiEvent volumePanelUiEvent12 = new VolumePanelUiEvent("VOLUME_PANEL_AUDIO_SHARING_SLIDER_TOUCHED", 11, 2068);
        VOLUME_PANEL_AUDIO_SHARING_SLIDER_TOUCHED = volumePanelUiEvent12;
        VolumePanelUiEvent volumePanelUiEvent13 = new VolumePanelUiEvent("VOLUME_PANEL_LIVE_CAPTION_TOGGLE_SHOWN", 12, 1644);
        VOLUME_PANEL_LIVE_CAPTION_TOGGLE_SHOWN = volumePanelUiEvent13;
        VolumePanelUiEvent volumePanelUiEvent14 = new VolumePanelUiEvent("VOLUME_PANEL_LIVE_CAPTION_TOGGLE_GONE", 13, 1645);
        VOLUME_PANEL_LIVE_CAPTION_TOGGLE_GONE = volumePanelUiEvent14;
        VolumePanelUiEvent volumePanelUiEvent15 = new VolumePanelUiEvent("VOLUME_PANEL_LIVE_CAPTION_TOGGLE_CLICKED", 14, 1646);
        VOLUME_PANEL_LIVE_CAPTION_TOGGLE_CLICKED = volumePanelUiEvent15;
        VolumePanelUiEvent volumePanelUiEvent16 = new VolumePanelUiEvent("VOLUME_PANEL_SPATIAL_AUDIO_BUTTON_SHOWN", 15, 1647);
        VolumePanelUiEvent volumePanelUiEvent17 = new VolumePanelUiEvent("VOLUME_PANEL_SPATIAL_AUDIO_BUTTON_GONE", 16, 1648);
        VolumePanelUiEvent volumePanelUiEvent18 = new VolumePanelUiEvent("VOLUME_PANEL_SPATIAL_AUDIO_POP_UP_SHOWN", 17, 1649);
        VOLUME_PANEL_SPATIAL_AUDIO_POP_UP_SHOWN = volumePanelUiEvent18;
        VolumePanelUiEvent volumePanelUiEvent19 = new VolumePanelUiEvent("VOLUME_PANEL_SPATIAL_AUDIO_TOGGLE_CLICKED", 18, 1650);
        VOLUME_PANEL_SPATIAL_AUDIO_TOGGLE_CLICKED = volumePanelUiEvent19;
        VolumePanelUiEvent volumePanelUiEvent20 = new VolumePanelUiEvent("VOLUME_PANEL_ANC_BUTTON_SHOWN", 19, 1651);
        VolumePanelUiEvent volumePanelUiEvent21 = new VolumePanelUiEvent("VOLUME_PANEL_ANC_BUTTON_GONE", 20, 1652);
        VolumePanelUiEvent volumePanelUiEvent22 = new VolumePanelUiEvent("VOLUME_PANEL_ANC_POPUP_SHOWN", 21, 1653);
        VOLUME_PANEL_ANC_POPUP_SHOWN = volumePanelUiEvent22;
        VolumePanelUiEvent[] volumePanelUiEventArr = {volumePanelUiEvent, volumePanelUiEvent2, volumePanelUiEvent3, volumePanelUiEvent4, volumePanelUiEvent5, volumePanelUiEvent6, volumePanelUiEvent7, volumePanelUiEvent8, volumePanelUiEvent9, volumePanelUiEvent10, volumePanelUiEvent11, volumePanelUiEvent12, volumePanelUiEvent13, volumePanelUiEvent14, volumePanelUiEvent15, volumePanelUiEvent16, volumePanelUiEvent17, volumePanelUiEvent18, volumePanelUiEvent19, volumePanelUiEvent20, volumePanelUiEvent21, volumePanelUiEvent22, new VolumePanelUiEvent("VOLUME_PANEL_ANC_TOGGLE_CLICKED", 22, 1654)};
        $VALUES = volumePanelUiEventArr;
        EnumEntriesKt.enumEntries(volumePanelUiEventArr);
        new Companion(null);
    }

    private VolumePanelUiEvent(String str, int i, int i2) {
        this.metricId = i2;
    }

    public static VolumePanelUiEvent valueOf(String str) {
        return (VolumePanelUiEvent) Enum.valueOf(VolumePanelUiEvent.class, str);
    }

    public static VolumePanelUiEvent[] values() {
        return (VolumePanelUiEvent[]) $VALUES.clone();
    }

    public final int getId() {
        return this.metricId;
    }
}
