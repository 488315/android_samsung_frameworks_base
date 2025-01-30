package com.android.internal.org.bouncycastle.asn1;

import java.io.InputStream;

/* loaded from: classes5.dex */
abstract class LimitedInputStream extends InputStream {
  protected final InputStream _in;
  private int _limit;

  LimitedInputStream(InputStream in, int limit) {
    this._in = in;
    this._limit = limit;
  }

  int getLimit() {
    return this._limit;
  }

  protected void setParentEofDetect(boolean on) {
    InputStream inputStream = this._in;
    if (inputStream instanceof IndefiniteLengthInputStream) {
      ((IndefiniteLengthInputStream) inputStream).setEofOn00(on);
    }
  }
}
