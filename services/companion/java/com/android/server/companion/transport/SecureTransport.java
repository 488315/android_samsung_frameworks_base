package com.android.server.companion.transport;

import android.content.Context;
import android.os.ParcelFileDescriptor;
import android.util.Slog;
import com.android.server.companion.securechannel.AttestationVerifier;
import com.android.server.companion.securechannel.SecureChannel;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/* loaded from: classes.dex */
public class SecureTransport extends Transport implements SecureChannel.Callback {
  public final BlockingQueue mRequestQueue;
  public final SecureChannel mSecureChannel;
  public volatile boolean mShouldProcessRequests;

  public SecureTransport(int i, ParcelFileDescriptor parcelFileDescriptor, Context context) {
    super(i, parcelFileDescriptor, context);
    this.mShouldProcessRequests = false;
    this.mRequestQueue = new ArrayBlockingQueue(100);
    this.mSecureChannel = new SecureChannel(this.mRemoteIn, this.mRemoteOut, this, context);
  }

  public SecureTransport(
      int i,
      ParcelFileDescriptor parcelFileDescriptor,
      Context context,
      byte[] bArr,
      AttestationVerifier attestationVerifier) {
    super(i, parcelFileDescriptor, context);
    this.mShouldProcessRequests = false;
    this.mRequestQueue = new ArrayBlockingQueue(100);
    this.mSecureChannel =
        new SecureChannel(this.mRemoteIn, this.mRemoteOut, this, bArr, attestationVerifier);
  }

  @Override // com.android.server.companion.transport.Transport
  public void start() {
    this.mSecureChannel.start();
  }

  @Override // com.android.server.companion.transport.Transport
  public void stop() {
    this.mSecureChannel.stop();
    this.mShouldProcessRequests = false;
  }

  @Override // com.android.server.companion.transport.Transport
  public void close() {
    this.mSecureChannel.close();
    this.mShouldProcessRequests = false;
    super.close();
  }

  @Override // com.android.server.companion.transport.Transport
  public void sendMessage(int i, int i2, byte[] bArr) {
    if (!this.mShouldProcessRequests) {
      establishSecureConnection();
    }
    if (Transport.DEBUG) {
      Slog.d(
          "CDM_CompanionTransport",
          "Queueing message 0x"
              + Integer.toHexString(i)
              + " sequence "
              + i2
              + " length "
              + bArr.length
              + " to association "
              + this.mAssociationId);
    }
    try {
      this.mRequestQueue.add(
          ByteBuffer.allocate(bArr.length + 12)
              .putInt(i)
              .putInt(i2)
              .putInt(bArr.length)
              .put(bArr)
              .array());
    } catch (IllegalStateException e) {
      Slog.w(
          "CDM_CompanionTransport",
          "Failed to queue message 0x"
              + Integer.toHexString(i)
              + " . Request buffer is full; detaching transport.",
          e);
      close();
    }
  }

  public final void establishSecureConnection() {
    Slog.d("CDM_CompanionTransport", "Establishing secure connection.");
    try {
      this.mSecureChannel.establishSecureConnection();
    } catch (Exception e) {
      Slog.e("CDM_CompanionTransport", "Failed to initiate secure channel handshake.", e);
      close();
    }
  }

  @Override // com.android.server.companion.securechannel.SecureChannel.Callback
  public void onSecureConnection() {
    this.mShouldProcessRequests = true;
    Slog.d("CDM_CompanionTransport", "Secure connection established.");
    new Thread(
            new Runnable() { // from class:
              // com.android.server.companion.transport.SecureTransport$$ExternalSyntheticLambda0
              @Override // java.lang.Runnable
              public final void run() {
                SecureTransport.this.lambda$onSecureConnection$0();
              }
            })
        .start();
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$onSecureConnection$0() {
    while (this.mShouldProcessRequests) {
      try {
        this.mSecureChannel.sendSecureMessage((byte[]) this.mRequestQueue.take());
      } catch (Exception e) {
        Slog.e("CDM_CompanionTransport", "Failed to send secure message.", e);
        close();
      }
    }
  }

  @Override // com.android.server.companion.securechannel.SecureChannel.Callback
  public void onSecureMessageReceived(byte[] bArr) {
    ByteBuffer wrap = ByteBuffer.wrap(bArr);
    int i = wrap.getInt();
    int i2 = wrap.getInt();
    byte[] bArr2 = new byte[wrap.getInt()];
    wrap.get(bArr2);
    try {
      handleMessage(i, i2, bArr2);
    } catch (IOException unused) {
    }
  }

  @Override // com.android.server.companion.securechannel.SecureChannel.Callback
  public void onError(Throwable th) {
    Slog.e("CDM_CompanionTransport", "Secure transport encountered an error.", th);
    if (this.mSecureChannel.isStopped()) {
      close();
    }
  }
}
