package android.media;

/* compiled from: WebVttRenderer.java */
/* loaded from: classes2.dex */
class TextTrackCueSpan {
    boolean mEnabled;
    String mText;
    long mTimestampMs;

    TextTrackCueSpan(String text, long timestamp) {
        this.mTimestampMs = timestamp;
        this.mText = text;
        this.mEnabled = timestamp < 0;
    }

    public boolean equals(Object o) {
        if (!(o instanceof TextTrackCueSpan)) {
            return false;
        }
        TextTrackCueSpan span = (TextTrackCueSpan) o;
        return this.mTimestampMs == span.mTimestampMs && this.mText.equals(span.mText);
    }
}
