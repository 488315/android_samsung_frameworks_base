package com.android.systemui.volume.panel.component.volume.domain.model;

import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import com.android.settingslib.volume.shared.model.AudioStream;
import com.android.systemui.volume.panel.component.mediaoutput.shared.model.MediaDeviceSession;
import java.util.Set;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public interface SliderType {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
            Set set = AudioStream.supportedStreamTypes;
            return this.stream == i;
        }

        public final int hashCode() {
            Set set = AudioStream.supportedStreamTypes;
            return Integer.hashCode(this.stream);
        }

        public final String toString() {
            return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Stream(stream=", AudioStream.m872toStringimpl(this.stream), ")");
        }

        private Stream(int i) {
            this.stream = i;
        }
    }
}
