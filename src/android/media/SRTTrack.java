package android.media;

import android.media.SubtitleTrack;
import android.os.Handler;
import android.os.Parcel;
import android.util.Log;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
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

    SRTTrack(WebVttRenderingWidget webVttRenderingWidget, MediaFormat mediaFormat) {
        super(webVttRenderingWidget, mediaFormat);
        this.mEventHandler = null;
    }

    SRTTrack(Handler handler, MediaFormat mediaFormat) {
        super(null, mediaFormat);
        this.mEventHandler = handler;
    }

    @Override // android.media.SubtitleTrack
    protected void onData(SubtitleData subtitleData) {
        try {
            TextTrackCue textTrackCue = new TextTrackCue();
            textTrackCue.mStartTimeMs = subtitleData.getStartTimeUs() / 1000;
            textTrackCue.mEndTimeMs = (subtitleData.getStartTimeUs() + subtitleData.getDurationUs()) / 1000;
            String[] strArrSplit = new String(subtitleData.getData(), "UTF-8").split("\\r?\\n");
            textTrackCue.mLines = new TextTrackCueSpan[strArrSplit.length][];
            int length = strArrSplit.length;
            int i = 0;
            int i2 = 0;
            while (i < length) {
                textTrackCue.mLines[i2] = new TextTrackCueSpan[]{new TextTrackCueSpan(strArrSplit[i], -1L)};
                i++;
                i2++;
            }
            addCue(textTrackCue);
        } catch (UnsupportedEncodingException e) {
            Log.w(TAG, "subtitle data is not UTF-8 encoded: " + e);
        }
    }

    @Override // android.media.WebVttTrack, android.media.SubtitleTrack
    public void onData(byte[] bArr, boolean z, long j) throws IOException {
        String line;
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(bArr), "UTF-8"));
            while (bufferedReader.readLine() != null && (line = bufferedReader.readLine()) != null) {
                TextTrackCue textTrackCue = new TextTrackCue();
                String[] strArrSplit = line.split("-->");
                textTrackCue.mStartTimeMs = parseMs(strArrSplit[0]);
                textTrackCue.mEndTimeMs = parseMs(strArrSplit[1]);
                textTrackCue.mRunID = j;
                ArrayList<String> arrayList = new ArrayList();
                while (true) {
                    String line2 = bufferedReader.readLine();
                    if (line2 == null || line2.trim().equals("")) {
                        break;
                    } else {
                        arrayList.add(line2);
                    }
                }
                textTrackCue.mLines = new TextTrackCueSpan[arrayList.size()][];
                textTrackCue.mStrings = (String[]) arrayList.toArray(new String[0]);
                int i = 0;
                for (String str : arrayList) {
                    TextTrackCueSpan[] textTrackCueSpanArr = {new TextTrackCueSpan(str, -1L)};
                    textTrackCue.mStrings[i] = str;
                    textTrackCue.mLines[i] = textTrackCueSpanArr;
                    i++;
                }
                addCue(textTrackCue);
            }
        } catch (UnsupportedEncodingException e) {
            Log.w(TAG, "subtitle data is not UTF-8 encoded: " + e);
        } catch (IOException e2) {
            Log.e(TAG, e2.getMessage(), e2);
        }
    }

    @Override // android.media.WebVttTrack, android.media.SubtitleTrack
    public void updateView(Vector<SubtitleTrack.Cue> vector) {
        if (getRenderingWidget() != null) {
            super.updateView(vector);
            return;
        }
        if (this.mEventHandler == null) {
            return;
        }
        Iterator<SubtitleTrack.Cue> it = vector.iterator();
        while (it.hasNext()) {
            SubtitleTrack.Cue next = it.next();
            TextTrackCue textTrackCue = (TextTrackCue) next;
            Parcel parcelObtain = Parcel.obtain();
            parcelObtain.writeInt(102);
            parcelObtain.writeInt(7);
            parcelObtain.writeInt((int) next.mStartTimeMs);
            parcelObtain.writeInt(16);
            StringBuilder sb = new StringBuilder();
            for (String str : textTrackCue.mStrings) {
                sb.append(str);
                sb.append('\n');
            }
            byte[] bytes = sb.toString().getBytes();
            parcelObtain.writeInt(bytes.length);
            parcelObtain.writeByteArray(bytes);
            this.mEventHandler.sendMessage(this.mEventHandler.obtainMessage(99, 0, 0, parcelObtain));
        }
        vector.clear();
    }

    private static long parseMs(String str) throws NumberFormatException {
        return (Long.parseLong(str.split(":")[0].trim()) * 3600000) + (Long.parseLong(str.split(":")[1].trim()) * 60000) + (Long.parseLong(str.split(":")[2].split(",")[0].trim()) * 1000) + Long.parseLong(str.split(":")[2].split(",")[1].trim());
    }
}
