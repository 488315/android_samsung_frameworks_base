package com.samsung.android.allshare;

import android.content.ContentResolver;
import android.p009os.Bundle;
import com.sec.android.allshare.iface.CVMessage;

/* loaded from: classes5.dex */
public interface IAllShareConnector {

  public enum AllShareServiceState {
    ALLSHARE_SERVICE_CONNECTED,
    ALLSHARE_SERVICE_DISCONNECTED
  }

  void connect();

  void destroyInstance();

  void disconnect();

  String getCaptionFilePathFromURI(String str);

  ContentResolver getContentResolver();

  boolean isAllShareServiceConnected();

  long requestCVMAsync(CVMessage cVMessage, AllShareResponseHandler allShareResponseHandler);

  CVMessage requestCVMSync(CVMessage cVMessage);

  boolean subscribeAllShareEvent(
      String str, Bundle bundle, AllShareEventHandler allShareEventHandler);

  void unsubscribeAllShareEvent(
      String str, Bundle bundle, AllShareEventHandler allShareEventHandler);
}
