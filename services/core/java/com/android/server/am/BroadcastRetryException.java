package com.android.server.am;

/* loaded from: classes.dex */
public class BroadcastRetryException extends BroadcastDeliveryFailedException {
  public BroadcastRetryException(String str) {
    super(str);
  }

  public BroadcastRetryException(Exception exc) {
    super(exc);
  }
}
