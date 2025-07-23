package android.media;

import android.app.slice.Slice;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.SubtitleTrack;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import java.util.Arrays;

/* compiled from: WebVttRenderer.java */
/* loaded from: classes2.dex */
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

    TextTrackCue() {
    }

    public boolean equals(Object obj) {
        boolean z;
        Integer num;
        if (!(obj instanceof TextTrackCue)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        try {
            TextTrackCue textTrackCue = (TextTrackCue) obj;
            boolean z2 = this.mId.equals(textTrackCue.mId) && this.mPauseOnExit == textTrackCue.mPauseOnExit && this.mWritingDirection == textTrackCue.mWritingDirection && this.mRegionId.equals(textTrackCue.mRegionId) && this.mSnapToLines == textTrackCue.mSnapToLines && (z = this.mAutoLinePosition) == textTrackCue.mAutoLinePosition && (z || (((num = this.mLinePosition) != null && num.equals(textTrackCue.mLinePosition)) || (this.mLinePosition == null && textTrackCue.mLinePosition == null))) && this.mTextPosition == textTrackCue.mTextPosition && this.mSize == textTrackCue.mSize && this.mAlignment == textTrackCue.mAlignment && this.mLines.length == textTrackCue.mLines.length;
            if (z2) {
                int i = 0;
                while (true) {
                    TextTrackCueSpan[][] textTrackCueSpanArr = this.mLines;
                    if (i >= textTrackCueSpanArr.length) {
                        break;
                    }
                    if (!Arrays.equals(textTrackCueSpanArr[i], textTrackCue.mLines[i])) {
                        return false;
                    }
                    i++;
                }
            }
            return z2;
        } catch (IncompatibleClassChangeError unused) {
            return false;
        }
    }

    public StringBuilder appendStringsToBuilder(StringBuilder sb) {
        if (this.mStrings == null) {
            sb.append(PerfettoProtoLogImpl.NULL_STRING);
            return sb;
        }
        sb.append(NavigationBarInflaterView.SIZE_MOD_START);
        String[] strArr = this.mStrings;
        int length = strArr.length;
        boolean z = true;
        int i = 0;
        while (i < length) {
            String str = strArr[i];
            if (!z) {
                sb.append(", ");
            }
            if (str == null) {
                sb.append(PerfettoProtoLogImpl.NULL_STRING);
            } else {
                sb.append("\"");
                sb.append(str);
                sb.append("\"");
            }
            i++;
            z = false;
        }
        sb.append(NavigationBarInflaterView.SIZE_MOD_END);
        return sb;
    }

    public StringBuilder appendLinesToBuilder(StringBuilder sb) {
        if (this.mLines == null) {
            sb.append(PerfettoProtoLogImpl.NULL_STRING);
            return sb;
        }
        sb.append(NavigationBarInflaterView.SIZE_MOD_START);
        TextTrackCueSpan[][] textTrackCueSpanArr = this.mLines;
        int length = textTrackCueSpanArr.length;
        int i = 0;
        boolean z = true;
        while (i < length) {
            TextTrackCueSpan[] textTrackCueSpanArr2 = textTrackCueSpanArr[i];
            if (!z) {
                sb.append(", ");
            }
            if (textTrackCueSpanArr2 == null) {
                sb.append(PerfettoProtoLogImpl.NULL_STRING);
            } else {
                sb.append("\"");
                int length2 = textTrackCueSpanArr2.length;
                long j = -1;
                int i2 = 0;
                boolean z2 = true;
                while (i2 < length2) {
                    TextTrackCueSpan textTrackCueSpan = textTrackCueSpanArr2[i2];
                    if (!z2) {
                        sb.append(" ");
                    }
                    if (textTrackCueSpan.mTimestampMs != j) {
                        sb.append("<");
                        sb.append(WebVttParser.timeToString(textTrackCueSpan.mTimestampMs));
                        sb.append(">");
                        j = textTrackCueSpan.mTimestampMs;
                    }
                    sb.append(textTrackCueSpan.mText);
                    i2++;
                    z2 = false;
                }
                sb.append("\"");
            }
            i++;
            z = false;
        }
        sb.append(NavigationBarInflaterView.SIZE_MOD_END);
        return sb;
    }

    public String toString() {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append(WebVttParser.timeToString(this.mStartTimeMs));
        sb.append(" --> ");
        sb.append(WebVttParser.timeToString(this.mEndTimeMs));
        sb.append(" {id:\"");
        sb.append(this.mId);
        sb.append("\", pauseOnExit:");
        sb.append(this.mPauseOnExit);
        sb.append(", direction:");
        int i = this.mWritingDirection;
        String str2 = "INVALID";
        if (i == 100) {
            str = Slice.HINT_HORIZONTAL;
        } else if (i == 102) {
            str = "vertical_lr";
        } else {
            str = i == 101 ? "vertical_rl" : "INVALID";
        }
        sb.append(str);
        sb.append(", regionId:\"");
        sb.append(this.mRegionId);
        sb.append("\", snapToLines:");
        sb.append(this.mSnapToLines);
        sb.append(", linePosition:");
        sb.append(this.mAutoLinePosition ? "auto" : this.mLinePosition);
        sb.append(", textPosition:");
        sb.append(this.mTextPosition);
        sb.append(", size:");
        sb.append(this.mSize);
        sb.append(", alignment:");
        int i2 = this.mAlignment;
        if (i2 == 202) {
            str2 = "end";
        } else if (i2 == 203) {
            str2 = "left";
        } else if (i2 == 200) {
            str2 = "middle";
        } else if (i2 == 204) {
            str2 = "right";
        } else if (i2 == 201) {
            str2 = "start";
        }
        sb.append(str2);
        sb.append(", text:");
        appendStringsToBuilder(sb).append("}");
        return sb.toString();
    }

    public int hashCode() {
        return toString().hashCode();
    }

    @Override // android.media.SubtitleTrack.Cue
    public void onTime(long j) {
        for (TextTrackCueSpan[] textTrackCueSpanArr : this.mLines) {
            for (TextTrackCueSpan textTrackCueSpan : textTrackCueSpanArr) {
                textTrackCueSpan.mEnabled = j >= textTrackCueSpan.mTimestampMs;
            }
        }
    }
}
