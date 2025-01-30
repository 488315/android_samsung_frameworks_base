package com.android.server.broadcastradio;

import android.hardware.broadcastradio.IBroadcastRadio;
import android.hardware.radio.IAnnouncementListener;
import android.hardware.radio.ICloseHandle;
import android.hardware.radio.IRadioService;
import android.hardware.radio.ITuner;
import android.hardware.radio.ITunerCallback;
import android.hardware.radio.RadioManager;
import android.os.Binder;
import android.os.ServiceManager;
import android.util.IndentingPrintWriter;
import android.util.Log;
import com.android.server.broadcastradio.aidl.BroadcastRadioServiceImpl;
import com.android.server.utils.Slogf;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/* loaded from: classes.dex */
public final class IRadioServiceAidlImpl extends IRadioService.Stub {
  public static final List SERVICE_NAMES;
  public final BroadcastRadioServiceImpl mHalAidl;
  public final BroadcastRadioService mService;

  static {
    StringBuilder sb = new StringBuilder();
    String str = IBroadcastRadio.DESCRIPTOR;
    sb.append(str);
    sb.append("/amfm");
    SERVICE_NAMES = Arrays.asList(sb.toString(), str + "/dab");
  }

  public static ArrayList getServicesNames() {
    ArrayList arrayList = new ArrayList();
    int i = 0;
    while (true) {
      List list = SERVICE_NAMES;
      if (i >= list.size()) {
        return arrayList;
      }
      if (ServiceManager.waitForDeclaredService((String) list.get(i)) != null) {
        arrayList.add((String) list.get(i));
      }
      i++;
    }
  }

  public IRadioServiceAidlImpl(BroadcastRadioService broadcastRadioService, ArrayList arrayList) {
    this(broadcastRadioService, new BroadcastRadioServiceImpl(arrayList));
    Slogf.m96i("BcRadioSrvAidl", "Initialize BroadcastRadioServiceAidl(%s)", broadcastRadioService);
  }

  public IRadioServiceAidlImpl(
      BroadcastRadioService broadcastRadioService,
      BroadcastRadioServiceImpl broadcastRadioServiceImpl) {
    Objects.requireNonNull(broadcastRadioService, "Broadcast radio service cannot be null");
    this.mService = broadcastRadioService;
    Objects.requireNonNull(
        broadcastRadioServiceImpl,
        "Broadcast radio service implementation for AIDL HAL cannot be null");
    this.mHalAidl = broadcastRadioServiceImpl;
  }

  public List listModules() {
    this.mService.enforcePolicyAccess();
    return this.mHalAidl.listModules();
  }

  public ITuner openTuner(
      int i, RadioManager.BandConfig bandConfig, boolean z, ITunerCallback iTunerCallback) {
    if (isDebugEnabled()) {
      Slogf.m88d("BcRadioSrvAidl", "Opening module %d", Integer.valueOf(i));
    }
    this.mService.enforcePolicyAccess();
    if (iTunerCallback == null) {
      throw new IllegalArgumentException("Callback must not be null");
    }
    return this.mHalAidl.openSession(i, bandConfig, z, iTunerCallback);
  }

  public ICloseHandle addAnnouncementListener(
      int[] iArr, IAnnouncementListener iAnnouncementListener) {
    if (isDebugEnabled()) {
      Slogf.m88d("BcRadioSrvAidl", "Adding announcement listener for %s", Arrays.toString(iArr));
    }
    Objects.requireNonNull(iArr, "Enabled announcement types cannot be null");
    Objects.requireNonNull(iAnnouncementListener, "Announcement listener cannot be null");
    this.mService.enforcePolicyAccess();
    return this.mHalAidl.addAnnouncementListener(iArr, iAnnouncementListener);
  }

  public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    if (this.mService.getContext().checkCallingOrSelfPermission("android.permission.DUMP") != 0) {
      printWriter.println(
          "Permission Denial: can't dump AIDL BroadcastRadioService from from pid="
              + Binder.getCallingPid()
              + ", uid="
              + Binder.getCallingUid()
              + " without permission android.permission.DUMP");
      return;
    }
    IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter);
    indentingPrintWriter.printf("BroadcastRadioService\n", new Object[0]);
    indentingPrintWriter.increaseIndent();
    indentingPrintWriter.printf("AIDL HAL:\n", new Object[0]);
    indentingPrintWriter.increaseIndent();
    this.mHalAidl.dumpInfo(indentingPrintWriter);
    indentingPrintWriter.decreaseIndent();
    indentingPrintWriter.decreaseIndent();
  }

  public static boolean isDebugEnabled() {
    return Log.isLoggable("BcRadioSrvAidl", 3);
  }
}
