package android.content.pm;

import android.os.UserHandle;
import java.util.List;

/* loaded from: classes.dex */
public abstract class CrossProfileAppsInternal {
  public abstract List<UserHandle> getTargetUserProfiles(String str, int i);

  public abstract void setInteractAcrossProfilesAppOp(String str, int i, int i2);

  public abstract boolean verifyPackageHasInteractAcrossProfilePermission(String str, int i)
      throws PackageManager.NameNotFoundException;

  public abstract boolean verifyUidHasInteractAcrossProfilePermission(String str, int i);
}
