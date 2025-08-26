package com.android.systemui.volume.panel.component.volume.domain.model;

import android.media.AudioSystem;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import com.android.settingslib.volume.shared.model.AudioStream;
import com.android.systemui.volume.panel.component.mediaoutput.shared.model.MediaDeviceSession;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public interface SliderType {

    public final class AudioSharingStream implements SliderType {
        static {
            new AudioSharingStream();
        }

        private AudioSharingStream() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof AudioSharingStream);
        }

        public final int hashCode() {
            return 16809646;
        }

        public final String toString() {
            return "AudioSharingStream";
        }
    }

    public final class MediaDeviceCast implements SliderType {
        public final MediaDeviceSession session;

        public MediaDeviceCast(MediaDeviceSession mediaDeviceSession) {
            this.session = mediaDeviceSession;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof MediaDeviceCast) && Intrinsics.areEqual(this.session, ((MediaDeviceCast) obj).session);
        }

        public final int hashCode() {
            return this.session.hashCode();
        }

        public final String toString() {
            return "MediaDeviceCast(session=" + this.session + ")";
        }
    }

    public final class Stream implements SliderType {
        public final int stream;

        public /* synthetic */ Stream(int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(i);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Stream)) {
                return false;
            }
            int i = ((Stream) obj).stream;
            AudioStream.Companion companion = AudioStream.Companion;
            return this.stream == i;
        }

        public final int hashCode() {
            AudioStream.Companion companion = AudioStream.Companion;
            return Integer.hashCode(this.stream);
        }

        public final String toString() {
            AudioStream.Companion companion = AudioStream.Companion;
            return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Stream(stream=", AudioSystem.streamToString(this.stream), ")");
        }

        private Stream(int i) {
            this.stream = i;
        }
    }
}
