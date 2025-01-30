package com.samsung.vekit.Listener;

import com.samsung.vekit.Common.Type.ErrorType;

/* loaded from: classes6.dex */
public interface PlayerStatusListener extends NativeInterfaceListener {
  void onCodecReclaim(long j);

  void onError(ErrorType errorType, long j);

  void onPlaybackCompleted();

  void onShowCompleted(long j);
}
