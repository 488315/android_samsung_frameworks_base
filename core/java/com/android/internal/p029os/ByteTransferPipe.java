package com.android.internal.p029os;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/* loaded from: classes5.dex */
public class ByteTransferPipe extends TransferPipe {
  static final String TAG = "ByteTransferPipe";
  private ByteArrayOutputStream mOutputStream;

  public ByteTransferPipe() throws IOException {}

  public ByteTransferPipe(String bufferPrefix) throws IOException {
    super(bufferPrefix, TAG);
  }

  @Override // com.android.internal.p029os.TransferPipe
  protected OutputStream getNewOutputStream() {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    this.mOutputStream = byteArrayOutputStream;
    return byteArrayOutputStream;
  }

  public byte[] get() throws IOException {
    m209go(null);
    return this.mOutputStream.toByteArray();
  }
}
