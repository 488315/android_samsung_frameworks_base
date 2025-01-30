package com.android.internal.org.bouncycastle.crypto;

/* loaded from: classes5.dex */
public interface SkippingCipher {
  long getPosition();

  long seekTo(long j);

  long skip(long j);
}
