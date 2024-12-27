package com.android.systemui.volume.panel.component.mediaoutput.domain.model;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import com.android.systemui.volume.domain.model.AudioOutputDevice;
import com.android.systemui.volume.panel.component.mediaoutput.shared.model.MediaDeviceSession;
import kotlin.jvm.internal.Intrinsics;

public interface MediaOutputComponentModel {

    public final class Calling implements MediaOutputComponentModel {
        public final AudioOutputDevice device;
        public final boolean isInAudioSharing;

        public Calling(AudioOutputDevice audioOutputDevice, boolean z) {
            this.device = audioOutputDevice;
            this.isInAudioSharing = z;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Calling)) {
                return false;
            }
            Calling calling = (Calling) obj;
            return Intrinsics.areEqual(this.device, calling.device) && this.isInAudioSharing == calling.isInAudioSharing;
        }

        @Override // com.android.systemui.volume.panel.component.mediaoutput.domain.model.MediaOutputComponentModel
        public final AudioOutputDevice getDevice() {
            return this.device;
        }

        public final int hashCode() {
            return Boolean.hashCode(this.isInAudioSharing) + (this.device.hashCode() * 31);
        }

        @Override // com.android.systemui.volume.panel.component.mediaoutput.domain.model.MediaOutputComponentModel
        public final boolean isInAudioSharing() {
            return this.isInAudioSharing;
        }

        public final String toString() {
            return "Calling(device=" + this.device + ", isInAudioSharing=" + this.isInAudioSharing + ")";
        }
    }

    public final class Idle implements MediaOutputComponentModel {
        public final AudioOutputDevice device;
        public final boolean isInAudioSharing;

        public Idle(AudioOutputDevice audioOutputDevice, boolean z) {
            this.device = audioOutputDevice;
            this.isInAudioSharing = z;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Idle)) {
                return false;
            }
            Idle idle = (Idle) obj;
            return Intrinsics.areEqual(this.device, idle.device) && this.isInAudioSharing == idle.isInAudioSharing;
        }

        @Override // com.android.systemui.volume.panel.component.mediaoutput.domain.model.MediaOutputComponentModel
        public final AudioOutputDevice getDevice() {
            return this.device;
        }

        public final int hashCode() {
            return Boolean.hashCode(this.isInAudioSharing) + (this.device.hashCode() * 31);
        }

        @Override // com.android.systemui.volume.panel.component.mediaoutput.domain.model.MediaOutputComponentModel
        public final boolean isInAudioSharing() {
            return this.isInAudioSharing;
        }

        public final String toString() {
            return "Idle(device=" + this.device + ", isInAudioSharing=" + this.isInAudioSharing + ")";
        }
    }

    public final class MediaSession implements MediaOutputComponentModel {
        public final AudioOutputDevice device;
        public final boolean isInAudioSharing;
        public final boolean isPlaybackActive;
        public final MediaDeviceSession session;

        public MediaSession(MediaDeviceSession mediaDeviceSession, boolean z, AudioOutputDevice audioOutputDevice, boolean z2) {
            this.session = mediaDeviceSession;
            this.isPlaybackActive = z;
            this.device = audioOutputDevice;
            this.isInAudioSharing = z2;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof MediaSession)) {
                return false;
            }
            MediaSession mediaSession = (MediaSession) obj;
            return Intrinsics.areEqual(this.session, mediaSession.session) && this.isPlaybackActive == mediaSession.isPlaybackActive && Intrinsics.areEqual(this.device, mediaSession.device) && this.isInAudioSharing == mediaSession.isInAudioSharing;
        }

        @Override // com.android.systemui.volume.panel.component.mediaoutput.domain.model.MediaOutputComponentModel
        public final AudioOutputDevice getDevice() {
            return this.device;
        }

        public final int hashCode() {
            return Boolean.hashCode(this.isInAudioSharing) + ((this.device.hashCode() + TransitionData$$ExternalSyntheticOutline0.m(this.session.hashCode() * 31, 31, this.isPlaybackActive)) * 31);
        }

        @Override // com.android.systemui.volume.panel.component.mediaoutput.domain.model.MediaOutputComponentModel
        public final boolean isInAudioSharing() {
            return this.isInAudioSharing;
        }

        public final String toString() {
            return "MediaSession(session=" + this.session + ", isPlaybackActive=" + this.isPlaybackActive + ", device=" + this.device + ", isInAudioSharing=" + this.isInAudioSharing + ")";
        }
    }

    AudioOutputDevice getDevice();

    boolean isInAudioSharing();
}
