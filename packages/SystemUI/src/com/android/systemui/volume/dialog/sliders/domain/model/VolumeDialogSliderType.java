package com.android.systemui.volume.dialog.sliders.domain.model;

import defpackage.ReorderTile$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface VolumeDialogSliderType {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class AudioSharingStream implements VolumeDialogSliderType {
        public final int audioStream;

        public AudioSharingStream(int i) {
            this.audioStream = i;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof AudioSharingStream) && this.audioStream == ((AudioSharingStream) obj).audioStream;
        }

        @Override // com.android.systemui.volume.dialog.sliders.domain.model.VolumeDialogSliderType
        public final int getAudioStream() {
            return this.audioStream;
        }

        public final int hashCode() {
            return Integer.hashCode(this.audioStream);
        }

        public final String toString() {
            return ReorderTile$$ExternalSyntheticOutline0.m(this.audioStream, ")", new StringBuilder("AudioSharingStream(audioStream="));
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class RemoteMediaStream implements VolumeDialogSliderType {
        public final int audioStream;

        public RemoteMediaStream(int i) {
            this.audioStream = i;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof RemoteMediaStream) && this.audioStream == ((RemoteMediaStream) obj).audioStream;
        }

        @Override // com.android.systemui.volume.dialog.sliders.domain.model.VolumeDialogSliderType
        public final int getAudioStream() {
            return this.audioStream;
        }

        public final int hashCode() {
            return Integer.hashCode(this.audioStream);
        }

        public final String toString() {
            return ReorderTile$$ExternalSyntheticOutline0.m(this.audioStream, ")", new StringBuilder("RemoteMediaStream(audioStream="));
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Stream implements VolumeDialogSliderType {
        public final int audioStream;

        public Stream(int i) {
            this.audioStream = i;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Stream) && this.audioStream == ((Stream) obj).audioStream;
        }

        @Override // com.android.systemui.volume.dialog.sliders.domain.model.VolumeDialogSliderType
        public final int getAudioStream() {
            return this.audioStream;
        }

        public final int hashCode() {
            return Integer.hashCode(this.audioStream);
        }

        public final String toString() {
            return ReorderTile$$ExternalSyntheticOutline0.m(this.audioStream, ")", new StringBuilder("Stream(audioStream="));
        }
    }

    int getAudioStream();
}
