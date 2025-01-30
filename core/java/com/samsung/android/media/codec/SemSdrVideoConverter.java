package com.samsung.android.media.codec;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.media.convert.core.Convert;
import com.samsung.android.media.convert.core.ConvertVideo;

/* loaded from: classes5.dex */
public class SemSdrVideoConverter {
  private static ConvertVideo mConvertVideo;
  private ProgressEventListener mProgressEventListener;

  public interface ProgressEventListener {
    void onCancelled();

    void onCompleted();

    void onFailed();

    void onStarted();
  }

  private SemSdrVideoConverter() {
    mConvertVideo = new ConvertVideo();
  }

  public static SemSdrVideoConverter create() {
    boolean result =
        SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_MMFW_SUPPORT_HDR2SDR");
    Log.m94d("SemSdrVideoConverter", "support HDR soltuion :" + result);
    if (result) {
      return new SemSdrVideoConverter();
    }
    return null;
  }

  public boolean initialize(String outputFilePath, String inputFilePath) {
    return mConvertVideo.initialize(outputFilePath, inputFilePath);
  }

  public boolean initialize(String outputFilePath, Context context, Uri inputUri) {
    return mConvertVideo.initialize(outputFilePath, context, inputUri);
  }

  public int getEstimatedOutputFileSize() {
    return mConvertVideo.getOutputFileSize();
  }

  public boolean convert() {
    return mConvertVideo.convert();
  }

  public boolean cancel() {
    return mConvertVideo.stop();
  }

  public void setProgressEventListener(ProgressEventListener listner) {
    this.mProgressEventListener = listner;
    mConvertVideo.setProgressUpdateListener(
        new Convert
            .ConvertEventListener() { // from class:
                                      // com.samsung.android.media.codec.SemSdrVideoConverter.1
          @Override // com.samsung.android.media.convert.core.Convert.ConvertEventListener
          public void onStarted() {
            SemSdrVideoConverter.this.mProgressEventListener.onStarted();
          }

          @Override // com.samsung.android.media.convert.core.Convert.ConvertEventListener
          public void onCompleted() {
            SemSdrVideoConverter.this.mProgressEventListener.onCompleted();
          }

          @Override // com.samsung.android.media.convert.core.Convert.ConvertEventListener
          public void onFailed() {
            SemSdrVideoConverter.this.mProgressEventListener.onFailed();
          }

          @Override // com.samsung.android.media.convert.core.Convert.ConvertEventListener
          public void onCancelled() {
            SemSdrVideoConverter.this.mProgressEventListener.onCancelled();
          }
        });
  }
}
