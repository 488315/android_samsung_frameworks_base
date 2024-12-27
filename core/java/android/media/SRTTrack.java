package android.media;

import android.media.SubtitleTrack;
import android.os.Handler;
import android.os.Message;
import android.os.Parcel;
import android.util.Log;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/* compiled from: SRTRenderer.java */
/* loaded from: classes2.dex */
class SRTTrack extends WebVttTrack {
    private static final int KEY_LOCAL_SETTING = 102;
    private static final int KEY_START_TIME = 7;
    private static final int KEY_STRUCT_TEXT = 16;
    private static final int MEDIA_TIMED_TEXT = 99;
    private static final String TAG = "SRTTrack";
    private final Handler mEventHandler;

    SRTTrack(WebVttRenderingWidget renderingWidget, MediaFormat format) {
        super(renderingWidget, format);
        this.mEventHandler = null;
    }

    SRTTrack(Handler eventHandler, MediaFormat format) {
        super(null, format);
        this.mEventHandler = eventHandler;
    }

    @Override // android.media.SubtitleTrack
    protected void onData(SubtitleData data) {
        try {
            TextTrackCue cue = new TextTrackCue();
            cue.mStartTimeMs = data.getStartTimeUs() / 1000;
            cue.mEndTimeMs = (data.getStartTimeUs() + data.getDurationUs()) / 1000;
            String paragraph = new String(data.getData(), "UTF-8");
            String[] lines = paragraph.split("\\r?\\n");
            cue.mLines = new TextTrackCueSpan[lines.length][];
            int i = 0;
            int length = lines.length;
            int i2 = 0;
            while (i2 < length) {
                String line = lines[i2];
                TextTrackCueSpan[] span = {new TextTrackCueSpan(line, -1L)};
                cue.mLines[i] = span;
                i2++;
                i++;
            }
            addCue(cue);
        } catch (UnsupportedEncodingException e) {
            Log.w(TAG, "subtitle data is not UTF-8 encoded: " + e);
        }
    }

    @Override // android.media.WebVttTrack, android.media.SubtitleTrack
    public void onData(byte[] data, boolean eos, long runID) {
        String header;
        try {
            Reader r = new InputStreamReader(new ByteArrayInputStream(data), "UTF-8");
            BufferedReader br = new BufferedReader(r);
            while (br.readLine() != null && (header = br.readLine()) != null) {
                TextTrackCue cue = new TextTrackCue();
                String[] startEnd = header.split("-->");
                cue.mStartTimeMs = parseMs(startEnd[0]);
                int i = 1;
                cue.mEndTimeMs = parseMs(startEnd[1]);
                try {
                    cue.mRunID = runID;
                    List<String> paragraph = new ArrayList<>();
                    while (true) {
                        String s = br.readLine();
                        if (s == null || s.trim().equals("")) {
                            break;
                        } else {
                            paragraph.add(s);
                        }
                    }
                    int i2 = 0;
                    cue.mLines = new TextTrackCueSpan[paragraph.size()][];
                    cue.mStrings = (String[]) paragraph.toArray(new String[0]);
                    for (String line : paragraph) {
                        TextTrackCueSpan[] span = new TextTrackCueSpan[i];
                        BufferedReader br2 = br;
                        span[0] = new TextTrackCueSpan(line, -1L);
                        cue.mStrings[i2] = line;
                        int i3 = i2 + 1;
                        cue.mLines[i2] = span;
                        i2 = i3;
                        br = br2;
                        i = 1;
                    }
                    BufferedReader br3 = br;
                    try {
                        addCue(cue);
                        br = br3;
                    } catch (UnsupportedEncodingException e) {
                        e = e;
                        Log.w(TAG, "subtitle data is not UTF-8 encoded: " + e);
                        return;
                    } catch (IOException e2) {
                        ioe = e2;
                        Log.e(TAG, ioe.getMessage(), ioe);
                        return;
                    }
                } catch (UnsupportedEncodingException e3) {
                    e = e3;
                } catch (IOException e4) {
                    ioe = e4;
                }
            }
        } catch (UnsupportedEncodingException e5) {
            e = e5;
        } catch (IOException e6) {
            ioe = e6;
        }
    }

    @Override // android.media.WebVttTrack, android.media.SubtitleTrack
    public void updateView(Vector<SubtitleTrack.Cue> activeCues) {
        if (getRenderingWidget() != null) {
            super.updateView(activeCues);
            return;
        }
        if (this.mEventHandler == null) {
            return;
        }
        Iterator<SubtitleTrack.Cue> it = activeCues.iterator();
        while (it.hasNext()) {
            SubtitleTrack.Cue cue = it.next();
            TextTrackCue ttc = (TextTrackCue) cue;
            Parcel parcel = Parcel.obtain();
            parcel.writeInt(102);
            parcel.writeInt(7);
            parcel.writeInt((int) cue.mStartTimeMs);
            parcel.writeInt(16);
            StringBuilder sb = new StringBuilder();
            for (String line : ttc.mStrings) {
                sb.append(line).append('\n');
            }
            byte[] buf = sb.toString().getBytes();
            parcel.writeInt(buf.length);
            parcel.writeByteArray(buf);
            Message msg = this.mEventHandler.obtainMessage(99, 0, 0, parcel);
            this.mEventHandler.sendMessage(msg);
        }
        activeCues.clear();
    }

    private static long parseMs(String in) {
        long hours = Long.parseLong(in.split(":")[0].trim());
        long minutes = Long.parseLong(in.split(":")[1].trim());
        long seconds = Long.parseLong(in.split(":")[2].split(",")[0].trim());
        long millies = Long.parseLong(in.split(":")[2].split(",")[1].trim());
        return (hours * 60 * 60 * 1000) + (60 * minutes * 1000) + (1000 * seconds) + millies;
    }
}
