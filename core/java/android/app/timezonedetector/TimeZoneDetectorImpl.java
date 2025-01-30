package android.app.timezonedetector;

import android.p009os.RemoteException;
import android.p009os.ServiceManager;

/* loaded from: classes.dex */
public final class TimeZoneDetectorImpl implements TimeZoneDetector {
  private static final boolean DEBUG = false;
  private static final String TAG = "timezonedetector.TimeZoneDetector";
  private final ITimeZoneDetectorService mITimeZoneDetectorService =
      ITimeZoneDetectorService.Stub.asInterface(
          ServiceManager.getServiceOrThrow("time_zone_detector"));

  @Override // android.app.timezonedetector.TimeZoneDetector
  public boolean suggestManualTimeZone(ManualTimeZoneSuggestion timeZoneSuggestion) {
    try {
      return this.mITimeZoneDetectorService.suggestManualTimeZone(timeZoneSuggestion);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Override // android.app.timezonedetector.TimeZoneDetector
  public void suggestTelephonyTimeZone(TelephonyTimeZoneSuggestion timeZoneSuggestion) {
    try {
      this.mITimeZoneDetectorService.suggestTelephonyTimeZone(timeZoneSuggestion);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }
}
