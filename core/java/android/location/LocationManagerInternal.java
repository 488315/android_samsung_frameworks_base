package android.location;

import android.location.util.identity.CallerIdentity;
import android.p009os.PackageTagsList;

/* loaded from: classes2.dex */
public abstract class LocationManagerInternal {

  public interface LocationPackageTagsListener {
    void onLocationPackageTagsChanged(int i, PackageTagsList packageTagsList);
  }

  public interface ProviderEnabledListener {
    void onProviderEnabledChanged(String str, int i, boolean z);
  }

  public abstract void addProviderEnabledListener(
      String str, ProviderEnabledListener providerEnabledListener);

  public abstract LocationTime getGnssTimeMillis();

  public abstract boolean isProvider(String str, CallerIdentity callerIdentity);

  public abstract boolean isProviderEnabledForUser(String str, int i);

  public abstract void removeProviderEnabledListener(
      String str, ProviderEnabledListener providerEnabledListener);

  public abstract void sendNiResponse(int i, int i2);

  public abstract void setLocationPackageTagsListener(
      LocationPackageTagsListener locationPackageTagsListener);
}
