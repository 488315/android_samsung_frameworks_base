package com.android.server.enterprise.knoxai;

import android.content.Context;
import android.util.Log;
import com.samsung.android.knox.knoxai.IKnoxAiManagerService;
import com.samsung.android.knox.knoxai.TaLoaderOptions;
import com.samsung.android.knox.knoxai.TaProcessBuffer;

/* loaded from: classes2.dex */
public class KnoxAiManagerService extends IKnoxAiManagerService.Stub {
  public static final String TAG = KnoxAiManagerService.class.getSimpleName();
  public static KnoxAiTzNative mNativeTzClient;
  public final Context context;

  public KnoxAiManagerService(Context context) {
    Log.d(TAG, "start KnoxAiManagerService: ");
    this.context = context;
    mNativeTzClient = new KnoxAiTzNative();
  }

  public int initializeTaSession(TaLoaderOptions taLoaderOptions) {
    Log.d(TAG, "initializeTaSession is called");
    return mNativeTzClient.initializeTaSession(taLoaderOptions);
  }

  public int terminateTaSession() {
    Log.d(TAG, "terminateTaSession is called");
    return mNativeTzClient.terminateTaSession();
  }

  public void processTaCommand(
      int i, TaProcessBuffer[] taProcessBufferArr, TaProcessBuffer[] taProcessBufferArr2) {
    Log.d(TAG, "processTaCommand is called");
    mNativeTzClient.processTaCommand(i, taProcessBufferArr, taProcessBufferArr2);
  }
}
