package com.android.server.credentials.metrics;

import android.util.Slog;
import com.android.server.credentials.metrics.shared.ResponseCollective;
import java.util.Map;

/* loaded from: classes.dex */
public class ChosenProviderFinalPhaseMetric {
  public final int mSessionIdCaller;
  public final int mSessionIdProvider;
  public boolean mUiReturned = false;
  public int mChosenUid = -1;
  public int mPreQueryPhaseLatencyMicroseconds = -1;
  public int mQueryPhaseLatencyMicroseconds = -1;
  public long mServiceBeganTimeNanoseconds = -1;
  public long mQueryStartTimeNanoseconds = -1;
  public long mQueryEndTimeNanoseconds = -1;
  public long mUiCallStartTimeNanoseconds = -1;
  public long mUiCallEndTimeNanoseconds = -1;
  public long mFinalFinishTimeNanoseconds = -1;
  public int mChosenProviderStatus = -1;
  public boolean mHasException = false;
  public String mFrameworkException = "";
  public ResponseCollective mResponseCollective = new ResponseCollective(Map.of(), Map.of());
  public boolean mIsPrimary = false;

  public ChosenProviderFinalPhaseMetric(int i, int i2) {
    this.mSessionIdCaller = i;
    this.mSessionIdProvider = i2;
  }

  public int getChosenUid() {
    return this.mChosenUid;
  }

  public void setChosenUid(int i) {
    this.mChosenUid = i;
  }

  public void setQueryPhaseLatencyMicroseconds(int i) {
    this.mQueryPhaseLatencyMicroseconds = i;
  }

  public void setServiceBeganTimeNanoseconds(long j) {
    this.mServiceBeganTimeNanoseconds = j;
  }

  public void setQueryStartTimeNanoseconds(long j) {
    this.mQueryStartTimeNanoseconds = j;
  }

  public void setQueryEndTimeNanoseconds(long j) {
    this.mQueryEndTimeNanoseconds = j;
  }

  public void setUiCallStartTimeNanoseconds(long j) {
    this.mUiCallStartTimeNanoseconds = j;
  }

  public void setUiCallEndTimeNanoseconds(long j) {
    this.mUiCallEndTimeNanoseconds = j;
  }

  public void setFinalFinishTimeNanoseconds(long j) {
    this.mFinalFinishTimeNanoseconds = j;
  }

  public long getQueryStartTimeNanoseconds() {
    return this.mQueryStartTimeNanoseconds;
  }

  public long getQueryEndTimeNanoseconds() {
    return this.mQueryEndTimeNanoseconds;
  }

  public long getUiCallStartTimeNanoseconds() {
    return this.mUiCallStartTimeNanoseconds;
  }

  public long getUiCallEndTimeNanoseconds() {
    return this.mUiCallEndTimeNanoseconds;
  }

  public long getFinalFinishTimeNanoseconds() {
    return this.mFinalFinishTimeNanoseconds;
  }

  public int getTimestampFromReferenceStartMicroseconds(long j) {
    long j2 = this.mServiceBeganTimeNanoseconds;
    if (j < j2) {
      Slog.i(
          "ChosenFinalPhaseMetric",
          "The timestamp is before service started, falling back to default int");
      return -1;
    }
    return (int) ((j - j2) / 1000);
  }

  public int getChosenProviderStatus() {
    return this.mChosenProviderStatus;
  }

  public void setChosenProviderStatus(int i) {
    this.mChosenProviderStatus = i;
  }

  public int getSessionIdProvider() {
    return this.mSessionIdProvider;
  }

  public void setUiReturned(boolean z) {
    this.mUiReturned = z;
  }

  public boolean isUiReturned() {
    return this.mUiReturned;
  }

  public void setHasException(boolean z) {
    this.mHasException = z;
  }

  public boolean isHasException() {
    return this.mHasException;
  }

  public void setResponseCollective(ResponseCollective responseCollective) {
    this.mResponseCollective = responseCollective;
  }

  public ResponseCollective getResponseCollective() {
    return this.mResponseCollective;
  }

  public void setFrameworkException(String str) {
    this.mFrameworkException = str;
  }

  public String getFrameworkException() {
    return this.mFrameworkException;
  }

  public int getSessionIdCaller() {
    return this.mSessionIdCaller;
  }

  public void setPrimary(boolean z) {
    this.mIsPrimary = z;
  }

  public boolean isPrimary() {
    return this.mIsPrimary;
  }
}
