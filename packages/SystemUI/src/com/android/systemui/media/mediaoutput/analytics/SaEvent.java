package com.android.systemui.media.mediaoutput.analytics;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class SaEvent {
    public final String id;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ActiveOutputDevice extends SaEvent {
        public static final ActiveOutputDevice INSTANCE = new ActiveOutputDevice();

        private ActiveOutputDevice() {
            super("Mo201", null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof ActiveOutputDevice);
        }

        public final int hashCode() {
            return -343055783;
        }

        public final String toString() {
            return "ActiveOutputDevice";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class AppIcon extends SaEvent {
        public static final AppIcon INSTANCE = new AppIcon();

        private AppIcon() {
            super("Mo101", null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof AppIcon);
        }

        public final int hashCode() {
            return 1078001214;
        }

        public final String toString() {
            return "AppIcon";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Casting extends SaEvent {
        public static final Casting INSTANCE = new Casting();

        private Casting() {
            super("Mo602", null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof Casting);
        }

        public final int hashCode() {
            return -1867338681;
        }

        public final String toString() {
            return "Casting";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ChangeAudioOutput extends SaEvent {
        public static final ChangeAudioOutput INSTANCE = new ChangeAudioOutput();

        private ChangeAudioOutput() {
            super("Mo208", null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof ChangeAudioOutput);
        }

        public final int hashCode() {
            return -925899349;
        }

        public final String toString() {
            return "ChangeAudioOutput";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ChangeAudioOutputOnTv extends SaEvent {
        public static final ChangeAudioOutputOnTv INSTANCE = new ChangeAudioOutputOnTv();

        private ChangeAudioOutputOnTv() {
            super("Mo906", null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof ChangeAudioOutputOnTv);
        }

        public final int hashCode() {
            return 1843702028;
        }

        public final String toString() {
            return "ChangeAudioOutputOnTv";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ChooseADeviceAction extends SaEvent {
        public static final ChooseADeviceAction INSTANCE = new ChooseADeviceAction();

        private ChooseADeviceAction() {
            super("Mo801", null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof ChooseADeviceAction);
        }

        public final int hashCode() {
            return -687130534;
        }

        public final String toString() {
            return "ChooseADeviceAction";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ChooseADevicePage extends SaEvent {
        public static final ChooseADevicePage INSTANCE = new ChooseADevicePage();

        private ChooseADevicePage() {
            super("Mo800", null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof ChooseADevicePage);
        }

        public final int hashCode() {
            return -1086302797;
        }

        public final String toString() {
            return "ChooseADevicePage";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ConnectedBt extends SaEvent {
        public static final ConnectedBt INSTANCE = new ConnectedBt();

        private ConnectedBt() {
            super("Mo206", null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof ConnectedBt);
        }

        public final int hashCode() {
            return -2058057921;
        }

        public final String toString() {
            return "ConnectedBt";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ConnectedBuds extends SaEvent {
        public static final ConnectedBuds INSTANCE = new ConnectedBuds();

        private ConnectedBuds() {
            super("Mo203", null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof ConnectedBuds);
        }

        public final int hashCode() {
            return -2108701745;
        }

        public final String toString() {
            return "ConnectedBuds";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class DisconnectedBt extends SaEvent {
        public static final DisconnectedBt INSTANCE = new DisconnectedBt();

        private DisconnectedBt() {
            super("Mo207", null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof DisconnectedBt);
        }

        public final int hashCode() {
            return 1319467881;
        }

        public final String toString() {
            return "DisconnectedBt";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class DisplayedSuggestedDevice extends SaEvent {
        public static final DisplayedSuggestedDevice INSTANCE = new DisplayedSuggestedDevice();

        private DisplayedSuggestedDevice() {
            super("Mo220", null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof DisplayedSuggestedDevice);
        }

        public final int hashCode() {
            return -1977808716;
        }

        public final String toString() {
            return "DisplayedSuggestedDevice";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class EndMusicShareClient extends SaEvent {
        public static final EndMusicShareClient INSTANCE = new EndMusicShareClient();

        private EndMusicShareClient() {
            super("Mo307", null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof EndMusicShareClient);
        }

        public final int hashCode() {
            return -2050225628;
        }

        public final String toString() {
            return "EndMusicShareClient";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class EndMusicShareHost extends SaEvent {
        public static final EndMusicShareHost INSTANCE = new EndMusicShareHost();

        private EndMusicShareHost() {
            super("Mo308", null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof EndMusicShareHost);
        }

        public final int hashCode() {
            return 1186844225;
        }

        public final String toString() {
            return "EndMusicShareHost";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class GroupWifiSpeaker extends SaEvent {
        public static final GroupWifiSpeaker INSTANCE = new GroupWifiSpeaker();

        private GroupWifiSpeaker() {
            super("Mo205", null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof GroupWifiSpeaker);
        }

        public final int hashCode() {
            return 1825934247;
        }

        public final String toString() {
            return "GroupWifiSpeaker";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class LaunchMediaOutput extends SaEvent {
        public static final LaunchMediaOutput INSTANCE = new LaunchMediaOutput();

        private LaunchMediaOutput() {
            super("Mo100", null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof LaunchMediaOutput);
        }

        public final int hashCode() {
            return -1484597418;
        }

        public final String toString() {
            return "LaunchMediaOutput";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class LaunchTvCard extends SaEvent {
        public static final LaunchTvCard INSTANCE = new LaunchTvCard();

        private LaunchTvCard() {
            super("Mo900", null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof LaunchTvCard);
        }

        public final int hashCode() {
            return -151109439;
        }

        public final String toString() {
            return "LaunchTvCard";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class MediaControlCustomButton1 extends SaEvent {
        public static final MediaControlCustomButton1 INSTANCE = new MediaControlCustomButton1();

        private MediaControlCustomButton1() {
            super("Mo102", null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof MediaControlCustomButton1);
        }

        public final int hashCode() {
            return 1454801209;
        }

        public final String toString() {
            return "MediaControlCustomButton1";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class MediaControlCustomButton2 extends SaEvent {
        public static final MediaControlCustomButton2 INSTANCE = new MediaControlCustomButton2();

        private MediaControlCustomButton2() {
            super("Mo106", null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof MediaControlCustomButton2);
        }

        public final int hashCode() {
            return 1454801210;
        }

        public final String toString() {
            return "MediaControlCustomButton2";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class MediaNext extends SaEvent {
        public static final MediaNext INSTANCE = new MediaNext();

        private MediaNext() {
            super("Mo105", null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof MediaNext);
        }

        public final int hashCode() {
            return 1814542331;
        }

        public final String toString() {
            return "MediaNext";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class MediaPlayPause extends SaEvent {
        public static final MediaPlayPause INSTANCE = new MediaPlayPause();

        private MediaPlayPause() {
            super("Mo104", null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof MediaPlayPause);
        }

        public final int hashCode() {
            return 1401045786;
        }

        public final String toString() {
            return "MediaPlayPause";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class MediaPrevious extends SaEvent {
        public static final MediaPrevious INSTANCE = new MediaPrevious();

        private MediaPrevious() {
            super("Mo103", null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof MediaPrevious);
        }

        public final int hashCode() {
            return -2137381889;
        }

        public final String toString() {
            return "MediaPrevious";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Mirroring extends SaEvent {
        public static final Mirroring INSTANCE = new Mirroring();

        private Mirroring() {
            super("Mo601", null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof Mirroring);
        }

        public final int hashCode() {
            return 7666375;
        }

        public final String toString() {
            return "Mirroring";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class MusicShareOnMyDevice extends SaEvent {
        public static final MusicShareOnMyDevice INSTANCE = new MusicShareOnMyDevice();

        private MusicShareOnMyDevice() {
            super("Mo302", null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof MusicShareOnMyDevice);
        }

        public final int hashCode() {
            return 1771397367;
        }

        public final String toString() {
            return "MusicShareOnMyDevice";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Mute extends SaEvent {
        public static final Mute INSTANCE = new Mute();

        private Mute() {
            super("Mo902", null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof Mute);
        }

        public final int hashCode() {
            return 2086249621;
        }

        public final String toString() {
            return "Mute";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class NumberOfApps extends SaEvent {
        public static final NumberOfApps INSTANCE = new NumberOfApps();

        private NumberOfApps() {
            super("Mo603", null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof NumberOfApps);
        }

        public final int hashCode() {
            return -367719410;
        }

        public final String toString() {
            return "NumberOfApps";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class NumberOfMusicShareDevice extends SaEvent {
        public static final NumberOfMusicShareDevice INSTANCE = new NumberOfMusicShareDevice();

        private NumberOfMusicShareDevice() {
            super("Mo304", null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof NumberOfMusicShareDevice);
        }

        public final int hashCode() {
            return -1859104948;
        }

        public final String toString() {
            return "NumberOfMusicShareDevice";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class OutputDevice extends SaEvent {
        public static final OutputDevice INSTANCE = new OutputDevice();

        private OutputDevice() {
            super("Mo200", null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof OutputDevice);
        }

        public final int hashCode() {
            return -1867860685;
        }

        public final String toString() {
            return "OutputDevice";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class PhoneSpeaker extends SaEvent {
        public static final PhoneSpeaker INSTANCE = new PhoneSpeaker();

        private PhoneSpeaker() {
            super("Mo202", null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof PhoneSpeaker);
        }

        public final int hashCode() {
            return 370309421;
        }

        public final String toString() {
            return "PhoneSpeaker";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class PlayMusicShare extends SaEvent {
        public static final PlayMusicShare INSTANCE = new PlayMusicShare();

        private PlayMusicShare() {
            super("Mo305", null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof PlayMusicShare);
        }

        public final int hashCode() {
            return 1728765578;
        }

        public final String toString() {
            return "PlayMusicShare";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class PlayPause extends SaEvent {
        public static final PlayPause INSTANCE = new PlayPause();

        private PlayPause() {
            super("Mo905", null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof PlayPause);
        }

        public final int hashCode() {
            return 2077320742;
        }

        public final String toString() {
            return "PlayPause";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class RemoteControl extends SaEvent {
        public static final RemoteControl INSTANCE = new RemoteControl();

        private RemoteControl() {
            super("Mo901", null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof RemoteControl);
        }

        public final int hashCode() {
            return -2555077;
        }

        public final String toString() {
            return "RemoteControl";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class SearchMusicShare extends SaEvent {
        public static final SearchMusicShare INSTANCE = new SearchMusicShare();

        private SearchMusicShare() {
            super("Mo303", null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof SearchMusicShare);
        }

        public final int hashCode() {
            return 1148012798;
        }

        public final String toString() {
            return "SearchMusicShare";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class SelectedSuggestedDevice extends SaEvent {
        public static final SelectedSuggestedDevice INSTANCE = new SelectedSuggestedDevice();

        private SelectedSuggestedDevice() {
            super("Mo222", null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof SelectedSuggestedDevice);
        }

        public final int hashCode() {
            return -1173115070;
        }

        public final String toString() {
            return "SelectedSuggestedDevice";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Setting extends SaEvent {
        public static final Setting INSTANCE = new Setting();

        private Setting() {
            super("Mo107", null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof Setting);
        }

        public final int hashCode() {
            return -436741548;
        }

        public final String toString() {
            return "Setting";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class SharedDevicesWithMusicShare extends SaEvent {
        public static final SharedDevicesWithMusicShare INSTANCE = new SharedDevicesWithMusicShare();

        private SharedDevicesWithMusicShare() {
            super("Mo301", null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof SharedDevicesWithMusicShare);
        }

        public final int hashCode() {
            return -151918852;
        }

        public final String toString() {
            return "SharedDevicesWithMusicShare";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ShowMusicShare extends SaEvent {
        public static final ShowMusicShare INSTANCE = new ShowMusicShare();

        private ShowMusicShare() {
            super("Mo502", null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof ShowMusicShare);
        }

        public final int hashCode() {
            return -1803172077;
        }

        public final String toString() {
            return "ShowMusicShare";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class SpotifyPlaybackPreference extends SaEvent {
        public static final SpotifyPlaybackPreference INSTANCE = new SpotifyPlaybackPreference();

        private SpotifyPlaybackPreference() {
            super("Mo503", null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof SpotifyPlaybackPreference);
        }

        public final int hashCode() {
            return -1253948236;
        }

        public final String toString() {
            return "SpotifyPlaybackPreference";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class StreamExpansionAdd extends SaEvent {
        public static final StreamExpansionAdd INSTANCE = new StreamExpansionAdd();

        private StreamExpansionAdd() {
            super("Mo402", null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof StreamExpansionAdd);
        }

        public final int hashCode() {
            return -6343554;
        }

        public final String toString() {
            return "StreamExpansionAdd";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class StreamExpansionRemove extends SaEvent {
        public static final StreamExpansionRemove INSTANCE = new StreamExpansionRemove();

        private StreamExpansionRemove() {
            super("Mo403", null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof StreamExpansionRemove);
        }

        public final int hashCode() {
            return 485741447;
        }

        public final String toString() {
            return "StreamExpansionRemove";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class TypeOfSuggestion extends SaEvent {
        public static final TypeOfSuggestion INSTANCE = new TypeOfSuggestion();

        private TypeOfSuggestion() {
            super("Mo221", null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof TypeOfSuggestion);
        }

        public final int hashCode() {
            return -43980783;
        }

        public final String toString() {
            return "TypeOfSuggestion";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class VolumeDown extends SaEvent {
        public static final VolumeDown INSTANCE = new VolumeDown();

        private VolumeDown() {
            super("Mo903", null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof VolumeDown);
        }

        public final int hashCode() {
            return 1234430040;
        }

        public final String toString() {
            return "VolumeDown";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class VolumeUp extends SaEvent {
        public static final VolumeUp INSTANCE = new VolumeUp();

        private VolumeUp() {
            super("Mo904", null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof VolumeUp);
        }

        public final int hashCode() {
            return 336480209;
        }

        public final String toString() {
            return "VolumeUp";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class WifiSpeaker extends SaEvent {
        public static final WifiSpeaker INSTANCE = new WifiSpeaker();

        private WifiSpeaker() {
            super("Mo204", null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof WifiSpeaker);
        }

        public final int hashCode() {
            return 555368750;
        }

        public final String toString() {
            return "WifiSpeaker";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class WifiSpeakerCheckBox extends SaEvent {
        public static final WifiSpeakerCheckBox INSTANCE = new WifiSpeakerCheckBox();

        private WifiSpeakerCheckBox() {
            super("Mo401", null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof WifiSpeakerCheckBox);
        }

        public final int hashCode() {
            return -888521999;
        }

        public final String toString() {
            return "WifiSpeakerCheckBox";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class WifiSpeakerPlaybackPreference extends SaEvent {
        public static final WifiSpeakerPlaybackPreference INSTANCE = new WifiSpeakerPlaybackPreference();

        private WifiSpeakerPlaybackPreference() {
            super("Mo501", null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof WifiSpeakerPlaybackPreference);
        }

        public final int hashCode() {
            return -1746331868;
        }

        public final String toString() {
            return "WifiSpeakerPlaybackPreference";
        }
    }

    public /* synthetic */ SaEvent(String str, DefaultConstructorMarker defaultConstructorMarker) {
        this(str);
    }

    private SaEvent(String str) {
        this.id = str;
    }
}
