package com.android.settingslib.volume.shared.model;

import android.media.AudioSystem;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import com.android.settingslib.volume.shared.model.AudioStream;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public interface AudioManagerEvent {

    public final class InternalRingerModeChanged implements AudioManagerEvent {
        public static final InternalRingerModeChanged INSTANCE = new InternalRingerModeChanged();

        private InternalRingerModeChanged() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof InternalRingerModeChanged);
        }

        public final int hashCode() {
            return 648091418;
        }

        public final String toString() {
            return "InternalRingerModeChanged";
        }
    }

    public final class StreamDevicesChanged implements AudioManagerEvent {
        public static final StreamDevicesChanged INSTANCE = new StreamDevicesChanged();

        private StreamDevicesChanged() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof StreamDevicesChanged);
        }

        public final int hashCode() {
            return 1307421940;
        }

        public final String toString() {
            return "StreamDevicesChanged";
        }
    }

    public final class StreamMasterMuteChanged implements AudioManagerEvent {
        public static final StreamMasterMuteChanged INSTANCE = new StreamMasterMuteChanged();

        private StreamMasterMuteChanged() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof StreamMasterMuteChanged);
        }

        public final int hashCode() {
            return 591006620;
        }

        public final String toString() {
            return "StreamMasterMuteChanged";
        }
    }

    public final class StreamMuteChanged implements StreamAudioManagerEvent {
        public final int audioStream;

        public /* synthetic */ StreamMuteChanged(int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(i);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof StreamMuteChanged)) {
                return false;
            }
            int i = ((StreamMuteChanged) obj).audioStream;
            AudioStream.Companion companion = AudioStream.Companion;
            return this.audioStream == i;
        }

        @Override // com.android.settingslib.volume.shared.model.StreamAudioManagerEvent
        /* renamed from: getAudioStream-2ffMKO0, reason: not valid java name */
        public final int mo989getAudioStream2ffMKO0() {
            return this.audioStream;
        }

        public final int hashCode() {
            AudioStream.Companion companion = AudioStream.Companion;
            return Integer.hashCode(this.audioStream);
        }

        public final String toString() {
            AudioStream.Companion companion = AudioStream.Companion;
            return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("StreamMuteChanged(audioStream=", AudioSystem.streamToString(this.audioStream), ")");
        }

        private StreamMuteChanged(int i) {
            this.audioStream = i;
        }
    }

    public final class StreamVolumeChanged implements StreamAudioManagerEvent {
        public final int audioStream;

        public /* synthetic */ StreamVolumeChanged(int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(i);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof StreamVolumeChanged)) {
                return false;
            }
            int i = ((StreamVolumeChanged) obj).audioStream;
            AudioStream.Companion companion = AudioStream.Companion;
            return this.audioStream == i;
        }

        @Override // com.android.settingslib.volume.shared.model.StreamAudioManagerEvent
        /* renamed from: getAudioStream-2ffMKO0 */
        public final int mo989getAudioStream2ffMKO0() {
            return this.audioStream;
        }

        public final int hashCode() {
            AudioStream.Companion companion = AudioStream.Companion;
            return Integer.hashCode(this.audioStream);
        }

        public final String toString() {
            AudioStream.Companion companion = AudioStream.Companion;
            return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("StreamVolumeChanged(audioStream=", AudioSystem.streamToString(this.audioStream), ")");
        }

        private StreamVolumeChanged(int i) {
            this.audioStream = i;
        }
    }
}
