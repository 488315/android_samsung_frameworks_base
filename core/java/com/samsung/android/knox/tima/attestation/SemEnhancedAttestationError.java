package com.samsung.android.knox.tima.attestation;

/* loaded from: classes5.dex */
public class SemEnhancedAttestationError {
  public static final int ERROR_BIND_FAIL = -7;
  public static final int ERROR_DEVICE_NOT_SUPPORTED = -4;
  public static final int ERROR_HTTP_BAD_REQUEST = 400;
  public static final int ERROR_HTTP_CONFLICT = 409;
  public static final int ERROR_HTTP_FORBIDDEN = 403;
  public static final int ERROR_HTTP_INTERNAL_SERVER = 500;
  public static final int ERROR_HTTP_NOT_FOUND = 404;
  public static final int ERROR_HTTP_SERVICE_UNAVAILABLE = 503;
  public static final int ERROR_HTTP_UNAUTHORIZED = 401;
  public static final int ERROR_INVALID_AUK = -6;
  public static final int ERROR_INVALID_NONCE = -5;
  public static final int ERROR_NETWORK_UNAVAILABLE = -8;
  public static final int ERROR_PERMISSION = -2;
  public static final int ERROR_TIMA_INTERNAL = -3;
  public static final int ERROR_UNKNOWN = -1;
  private EnhancedAttestationResult result;

  public SemEnhancedAttestationError(EnhancedAttestationResult result) {
    this.result = result;
  }

  public int getError() {
    EnhancedAttestationResult enhancedAttestationResult = this.result;
    if (enhancedAttestationResult != null) {
      return enhancedAttestationResult.getError();
    }
    return -1;
  }

  public String getReason() {
    EnhancedAttestationResult enhancedAttestationResult = this.result;
    if (enhancedAttestationResult != null) {
      return enhancedAttestationResult.getReason();
    }
    return null;
  }

  public String getUniqueId() {
    EnhancedAttestationResult enhancedAttestationResult = this.result;
    if (enhancedAttestationResult != null) {
      return enhancedAttestationResult.getUniqueId();
    }
    return null;
  }

  public String getResponseId() {
    EnhancedAttestationResult enhancedAttestationResult = this.result;
    if (enhancedAttestationResult != null) {
      return enhancedAttestationResult.getServerResponseId();
    }
    return null;
  }

  public int getRetryAfter() {
    EnhancedAttestationResult enhancedAttestationResult = this.result;
    if (enhancedAttestationResult != null) {
      return enhancedAttestationResult.getRetryAfterTime();
    }
    return -1;
  }
}
