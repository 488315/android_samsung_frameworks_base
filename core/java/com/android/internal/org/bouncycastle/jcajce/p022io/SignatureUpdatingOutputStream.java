package com.android.internal.org.bouncycastle.jcajce.p022io;

import java.io.IOException;
import java.io.OutputStream;
import java.security.Signature;
import java.security.SignatureException;

/* loaded from: classes5.dex */
class SignatureUpdatingOutputStream extends OutputStream {
  private Signature sig;

  SignatureUpdatingOutputStream(Signature sig) {
    this.sig = sig;
  }

  @Override // java.io.OutputStream
  public void write(byte[] bytes, int off, int len) throws IOException {
    try {
      this.sig.update(bytes, off, len);
    } catch (SignatureException e) {
      throw new IOException(e.getMessage());
    }
  }

  @Override // java.io.OutputStream
  public void write(byte[] bytes) throws IOException {
    try {
      this.sig.update(bytes);
    } catch (SignatureException e) {
      throw new IOException(e.getMessage());
    }
  }

  @Override // java.io.OutputStream
  public void write(int b) throws IOException {
    try {
      this.sig.update((byte) b);
    } catch (SignatureException e) {
      throw new IOException(e.getMessage());
    }
  }
}
