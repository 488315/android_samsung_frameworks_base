package android.p009os;

import android.util.Log;
import java.time.Clock;
import java.time.DateTimeException;
import java.time.ZoneId;
import java.util.Arrays;

/* loaded from: classes3.dex */
public class BestClock extends SimpleClock {
  private static final String TAG = "BestClock";
  private final Clock[] clocks;

  public BestClock(ZoneId zone, Clock... clocks) {
    super(zone);
    this.clocks = clocks;
  }

  @Override // android.p009os.SimpleClock, java.time.Clock, java.time.InstantSource
  public long millis() {
    Clock[] clockArr = this.clocks;
    int length = clockArr.length;
    for (int i = 0; i < length; i++) {
      Clock clock = clockArr[i];
      try {
        return clock.millis();
      } catch (DateTimeException e) {
        Log.m102w(TAG, e.toString());
      }
    }
    throw new DateTimeException(
        "No clocks in " + Arrays.toString(this.clocks) + " were able to provide time");
  }
}
