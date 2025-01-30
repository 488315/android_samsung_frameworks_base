package com.android.server.credentials.metrics;

import com.android.server.audio.AudioService$$ExternalSyntheticLambda0;
import java.util.LinkedHashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class InitialPhaseMetric {
  public final int mSessionIdCaller;
  public int mApiName = ApiName.UNKNOWN.getMetricCode();
  public int mCallerUid = -1;
  public long mCredentialServiceStartedTimeNanoseconds = -1;
  public long mCredentialServiceBeginQueryTimeNanoseconds = -1;
  public boolean mOriginSpecified = false;
  public Map mRequestCounts = new LinkedHashMap();

  public InitialPhaseMetric(int i) {
    this.mSessionIdCaller = i;
  }

  public void setCredentialServiceStartedTimeNanoseconds(long j) {
    this.mCredentialServiceStartedTimeNanoseconds = j;
  }

  public void setCredentialServiceBeginQueryTimeNanoseconds(long j) {
    this.mCredentialServiceBeginQueryTimeNanoseconds = j;
  }

  public long getCredentialServiceStartedTimeNanoseconds() {
    return this.mCredentialServiceStartedTimeNanoseconds;
  }

  public void setApiName(int i) {
    this.mApiName = i;
  }

  public int getApiName() {
    return this.mApiName;
  }

  public void setCallerUid(int i) {
    this.mCallerUid = i;
  }

  public int getCallerUid() {
    return this.mCallerUid;
  }

  public int getSessionIdCaller() {
    return this.mSessionIdCaller;
  }

  public int getCountRequestClassType() {
    return this.mRequestCounts.size();
  }

  public void setOriginSpecified(boolean z) {
    this.mOriginSpecified = z;
  }

  public boolean isOriginSpecified() {
    return this.mOriginSpecified;
  }

  public void setRequestCounts(Map map) {
    this.mRequestCounts = map;
  }

  public String[] getUniqueRequestStrings() {
    String[] strArr = new String[this.mRequestCounts.keySet().size()];
    this.mRequestCounts.keySet().toArray(strArr);
    return strArr;
  }

  public int[] getUniqueRequestCounts() {
    return this.mRequestCounts.values().stream()
        .mapToInt(new AudioService$$ExternalSyntheticLambda0())
        .toArray();
  }
}
