package com.android.systemui.screenrecord;

import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.media.MediaMuxer;
import android.support.v4.media.AbstractC0000x2c234b15;
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

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
        ExifInterface$$ExternalSyntheticOutline0.m35m(ActivityResultRegistry$$ExternalSyntheticOutline0.m4m("out: ", str, " , in: "), strArr[0], "ScreenRecordingMuxer");
    }

    public final void mux() {
        ArrayList arrayList;
        ArrayMap arrayMap;
        MediaMuxer mediaMuxer = new MediaMuxer(this.mOutFile, this.mFormat);
        String[] strArr = this.mFiles;
        int length = strArr.length;
        int i = 0;
        while (true) {
            arrayList = this.mExtractors;
            arrayMap = this.mExtractorIndexToMuxerIndex;
            if (i >= length) {
                break;
            }
            String str = strArr[i];
            MediaExtractor mediaExtractor = new MediaExtractor();
            try {
                mediaExtractor.setDataSource(str);
                StringBuilder m2m = AbstractC0000x2c234b15.m2m(str, " track count: ");
                m2m.append(mediaExtractor.getTrackCount());
                Log.d("ScreenRecordingMuxer", m2m.toString());
                arrayList.add(mediaExtractor);
                for (int i2 = 0; i2 < mediaExtractor.getTrackCount(); i2++) {
                    int addTrack = mediaMuxer.addTrack(mediaExtractor.getTrackFormat(i2));
                    Log.d("ScreenRecordingMuxer", "created extractor format" + mediaExtractor.getTrackFormat(i2).toString());
                    arrayMap.put(Pair.create(mediaExtractor, Integer.valueOf(i2)), Integer.valueOf(addTrack));
                }
            } catch (IOException e) {
                Log.e("ScreenRecordingMuxer", "error creating extractor: " + str);
                e.printStackTrace();
            }
            i++;
        }
        mediaMuxer.start();
        for (Pair pair : arrayMap.keySet()) {
            MediaExtractor mediaExtractor2 = (MediaExtractor) pair.first;
            mediaExtractor2.selectTrack(((Integer) pair.second).intValue());
            int intValue = ((Integer) arrayMap.get(pair)).intValue();
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
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ((MediaExtractor) it.next()).release();
        }
        mediaMuxer.stop();
        mediaMuxer.release();
    }
}
