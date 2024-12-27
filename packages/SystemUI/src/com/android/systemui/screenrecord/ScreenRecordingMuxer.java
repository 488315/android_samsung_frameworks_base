package com.android.systemui.screenrecord;

import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.media.MediaMuxer;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Pair;
import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ScreenRecordingMuxer {
    public final ArrayMap mExtractorIndexToMuxerIndex = new ArrayMap();
    public final ArrayList mExtractors = new ArrayList();
    public final String[] mFiles;
    public final int mFormat;
    public final String mOutFile;

    public ScreenRecordingMuxer(int i, String str, String... strArr) {
        this.mFiles = strArr;
        this.mOutFile = str;
        this.mFormat = i;
        ExifInterface$$ExternalSyntheticOutline0.m(ActivityResultRegistry$$ExternalSyntheticOutline0.m("out: ", str, " , in: "), strArr[0], "ScreenRecordingMuxer");
    }

    public final void mux() {
        MediaMuxer mediaMuxer = new MediaMuxer(this.mOutFile, this.mFormat);
        for (String str : this.mFiles) {
            MediaExtractor mediaExtractor = new MediaExtractor();
            try {
                mediaExtractor.setDataSource(str);
                StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(str, " track count: ");
                m.append(mediaExtractor.getTrackCount());
                Log.d("ScreenRecordingMuxer", m.toString());
                this.mExtractors.add(mediaExtractor);
                for (int i = 0; i < mediaExtractor.getTrackCount(); i++) {
                    int addTrack = mediaMuxer.addTrack(mediaExtractor.getTrackFormat(i));
                    Log.d("ScreenRecordingMuxer", "created extractor format" + mediaExtractor.getTrackFormat(i).toString());
                    this.mExtractorIndexToMuxerIndex.put(Pair.create(mediaExtractor, Integer.valueOf(i)), Integer.valueOf(addTrack));
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
            ByteBuffer allocate = ByteBuffer.allocate(QuickStepContract.SYSUI_STATE_BACK_DISABLED);
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
        Iterator it = this.mExtractors.iterator();
        while (it.hasNext()) {
            ((MediaExtractor) it.next()).release();
        }
        mediaMuxer.stop();
        mediaMuxer.release();
    }
}
