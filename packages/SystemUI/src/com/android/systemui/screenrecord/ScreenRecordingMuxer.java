package com.android.systemui.screenrecord;

import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.media.MediaMuxer;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Pair;
import androidx.activity.result.ActivityResultRegistry$register$3$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class ScreenRecordingMuxer {
    public final ArrayMap mExtractorIndexToMuxerIndex = new ArrayMap();
    public final ArrayList mExtractors = new ArrayList();
    public final String[] mFiles;
    public final int mFormat;
    public final String mOutFile;

    public ScreenRecordingMuxer(int i, String str, String... strArr) {
        this.mFiles = strArr;
        this.mOutFile = str;
        this.mFormat = i;
        ExifInterface$$ExternalSyntheticOutline0.m(ActivityResultRegistry$register$3$$ExternalSyntheticOutline0.m("out: ", str, " , in: "), strArr[0], "ScreenRecordingMuxer");
    }

    public final void mux() {
        MediaMuxer mediaMuxer = new MediaMuxer(this.mOutFile, this.mFormat);
        int i = 0;
        for (String str : this.mFiles) {
            MediaExtractor mediaExtractor = new MediaExtractor();
            try {
                mediaExtractor.setDataSource(str);
                StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(str, " track count: ");
                m.append(mediaExtractor.getTrackCount());
                Log.d("ScreenRecordingMuxer", m.toString());
                this.mExtractors.add(mediaExtractor);
                for (int i2 = 0; i2 < mediaExtractor.getTrackCount(); i2++) {
                    int addTrack = mediaMuxer.addTrack(mediaExtractor.getTrackFormat(i2));
                    Log.d("ScreenRecordingMuxer", "created extractor format" + mediaExtractor.getTrackFormat(i2).toString());
                    this.mExtractorIndexToMuxerIndex.put(Pair.create(mediaExtractor, Integer.valueOf(i2)), Integer.valueOf(addTrack));
                }
            } catch (IOException e) {
                Log.e("ScreenRecordingMuxer", "error creating extractor: " + str);
                e.printStackTrace();
            }
        }
        mediaMuxer.start();
        for (Pair pair : this.mExtractorIndexToMuxerIndex.keySet()) {
            MediaExtractor mediaExtractor2 = (MediaExtractor) pair.first;
            mediaExtractor2.selectTrack(((Integer) pair.second).intValue());
            int intValue = ((Integer) this.mExtractorIndexToMuxerIndex.get(pair)).intValue();
            Log.d("ScreenRecordingMuxer", "track format: " + mediaExtractor2.getTrackFormat(((Integer) pair.second).intValue()));
            mediaExtractor2.seekTo(0L, 2);
            ByteBuffer allocate = ByteBuffer.allocate(4194304);
            MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
            while (true) {
                int readSampleData = mediaExtractor2.readSampleData(allocate, allocate.arrayOffset());
                bufferInfo.size = readSampleData;
                if (readSampleData < 0) {
                    break;
                }
                bufferInfo.presentationTimeUs = mediaExtractor2.getSampleTime();
                bufferInfo.flags = mediaExtractor2.getSampleFlags();
                mediaMuxer.writeSampleData(intValue, allocate, bufferInfo);
                mediaExtractor2.advance();
            }
        }
        ArrayList arrayList = this.mExtractors;
        int size = arrayList.size();
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((MediaExtractor) obj).release();
        }
        mediaMuxer.stop();
        mediaMuxer.release();
    }
}
