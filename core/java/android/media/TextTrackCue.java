package android.media;

import android.app.slice.Slice;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;

import java.util.Arrays;

/* compiled from: WebVttRenderer.java */
class TextTrackCue extends SubtitleTrack.Cue {
    static final int ALIGNMENT_END = 202;
    static final int ALIGNMENT_LEFT = 203;
    static final int ALIGNMENT_MIDDLE = 200;
    static final int ALIGNMENT_RIGHT = 204;
    static final int ALIGNMENT_START = 201;
    private static final String TAG = "TTCue";
    static final int WRITING_DIRECTION_HORIZONTAL = 100;
    static final int WRITING_DIRECTION_VERTICAL_LR = 102;
    static final int WRITING_DIRECTION_VERTICAL_RL = 101;
    boolean mAutoLinePosition;
    String[] mStrings;
    String mId = "";
    boolean mPauseOnExit = false;
    int mWritingDirection = 100;
    String mRegionId = "";
    boolean mSnapToLines = true;
    Integer mLinePosition = null;
    int mTextPosition = 50;
    int mSize = 100;
    int mAlignment = 200;
    TextTrackCueSpan[][] mLines = null;
    TextTrackRegion mRegion = null;

    TextTrackCue() {}

    public boolean equals(Object o) {
        if (!(o instanceof TextTrackCue)) {
            return false;
        }
        if (this == o) {
            return true;
        }
        try {
            TextTrackCue cue = (TextTrackCue) o;
            boolean res =
                    this.mId.equals(cue.mId)
                            && this.mPauseOnExit == cue.mPauseOnExit
                            && this.mWritingDirection == cue.mWritingDirection
                            && this.mRegionId.equals(cue.mRegionId)
                            && this.mSnapToLines == cue.mSnapToLines
                            && this.mAutoLinePosition == cue.mAutoLinePosition
                            && (this.mAutoLinePosition
                                    || ((this.mLinePosition != null
                                                    && this.mLinePosition.equals(cue.mLinePosition))
                                            || (this.mLinePosition == null
                                                    && cue.mLinePosition == null)))
                            && this.mTextPosition == cue.mTextPosition
                            && this.mSize == cue.mSize
                            && this.mAlignment == cue.mAlignment
                            && this.mLines.length == cue.mLines.length;
            if (res) {
                for (int line = 0; line < this.mLines.length; line++) {
                    if (!Arrays.equals(this.mLines[line], cue.mLines[line])) {
                        return false;
                    }
                }
            }
            return res;
        } catch (IncompatibleClassChangeError e) {
            return false;
        }
    }

    public StringBuilder appendStringsToBuilder(StringBuilder builder) {
        if (this.mStrings == null) {
            builder.append("null");
        } else {
            builder.append(NavigationBarInflaterView.SIZE_MOD_START);
            boolean first = true;
            for (String s : this.mStrings) {
                if (!first) {
                    builder.append(", ");
                }
                if (s == null) {
                    builder.append("null");
                } else {
                    builder.append("\"");
                    builder.append(s);
                    builder.append("\"");
                }
                first = false;
            }
            builder.append(NavigationBarInflaterView.SIZE_MOD_END);
        }
        return builder;
    }

    public StringBuilder appendLinesToBuilder(StringBuilder builder) {
        String str;
        TextTrackCueSpan[][] textTrackCueSpanArr;
        TextTrackCueSpan[][] textTrackCueSpanArr2;
        String str2 = "null";
        if (this.mLines == null) {
            builder.append("null");
        } else {
            builder.append(NavigationBarInflaterView.SIZE_MOD_START);
            boolean first = true;
            TextTrackCueSpan[][] textTrackCueSpanArr3 = this.mLines;
            int length = textTrackCueSpanArr3.length;
            int i = 0;
            while (i < length) {
                TextTrackCueSpan[] spans = textTrackCueSpanArr3[i];
                if (!first) {
                    builder.append(", ");
                }
                if (spans == null) {
                    builder.append(str2);
                    str = str2;
                    textTrackCueSpanArr = textTrackCueSpanArr3;
                } else {
                    builder.append("\"");
                    boolean innerFirst = true;
                    long lastTimestamp = -1;
                    int length2 = spans.length;
                    int i2 = 0;
                    while (i2 < length2) {
                        TextTrackCueSpan span = spans[i2];
                        if (!innerFirst) {
                            builder.append(" ");
                        }
                        boolean first2 = first;
                        String str3 = str2;
                        if (span.mTimestampMs == lastTimestamp) {
                            textTrackCueSpanArr2 = textTrackCueSpanArr3;
                        } else {
                            textTrackCueSpanArr2 = textTrackCueSpanArr3;
                            builder.append("<")
                                    .append(WebVttParser.timeToString(span.mTimestampMs))
                                    .append(">");
                            lastTimestamp = span.mTimestampMs;
                        }
                        builder.append(span.mText);
                        innerFirst = false;
                        i2++;
                        str2 = str3;
                        first = first2;
                        textTrackCueSpanArr3 = textTrackCueSpanArr2;
                    }
                    str = str2;
                    textTrackCueSpanArr = textTrackCueSpanArr3;
                    builder.append("\"");
                }
                first = false;
                i++;
                str2 = str;
                textTrackCueSpanArr3 = textTrackCueSpanArr;
            }
            builder.append(NavigationBarInflaterView.SIZE_MOD_END);
        }
        return builder;
    }

    public String toString() {
        String str;
        StringBuilder res = new StringBuilder();
        StringBuilder append =
                res.append(WebVttParser.timeToString(this.mStartTimeMs))
                        .append(" --> ")
                        .append(WebVttParser.timeToString(this.mEndTimeMs))
                        .append(" {id:\"")
                        .append(this.mId)
                        .append("\", pauseOnExit:")
                        .append(this.mPauseOnExit)
                        .append(", direction:");
        String str2 = "INVALID";
        if (this.mWritingDirection == 100) {
            str = Slice.HINT_HORIZONTAL;
        } else if (this.mWritingDirection == 102) {
            str = "vertical_lr";
        } else {
            str = this.mWritingDirection == 101 ? "vertical_rl" : "INVALID";
        }
        StringBuilder append2 =
                append.append(str)
                        .append(", regionId:\"")
                        .append(this.mRegionId)
                        .append("\", snapToLines:")
                        .append(this.mSnapToLines)
                        .append(", linePosition:")
                        .append(this.mAutoLinePosition ? "auto" : this.mLinePosition)
                        .append(", textPosition:")
                        .append(this.mTextPosition)
                        .append(", size:")
                        .append(this.mSize)
                        .append(", alignment:");
        if (this.mAlignment == 202) {
            str2 = "end";
        } else if (this.mAlignment == 203) {
            str2 = "left";
        } else if (this.mAlignment == 200) {
            str2 = "middle";
        } else if (this.mAlignment == 204) {
            str2 = "right";
        } else if (this.mAlignment == 201) {
            str2 = "start";
        }
        append2.append(str2).append(", text:");
        appendStringsToBuilder(res).append("}");
        return res.toString();
    }

    public int hashCode() {
        return toString().hashCode();
    }

    @Override // android.media.SubtitleTrack.Cue
    public void onTime(long timeMs) {
        for (TextTrackCueSpan[] line : this.mLines) {
            for (TextTrackCueSpan span : line) {
                span.mEnabled = timeMs >= span.mTimestampMs;
            }
        }
    }
}
