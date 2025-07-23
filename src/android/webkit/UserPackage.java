package android.webkit;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.UserHandle;
import android.os.UserManager;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class UserPackage {
    private final PackageInfo mPackageInfo;
    private final UserHandle mUser;

    public UserPackage(UserHandle userHandle, PackageInfo packageInfo) {
        this.mUser = userHandle;
        this.mPackageInfo = packageInfo;
    }

    public static List<UserPackage> getPackageInfosAllUsers(Context context, String str, int i) {
        PackageInfo packageInfo;
        List<UserHandle> userHandles = ((UserManager) context.getSystemService(UserManager.class)).getUserHandles(false);
        ArrayList arrayList = new ArrayList(userHandles.size());
        for (UserHandle userHandle : userHandles) {
            try {
                packageInfo = context.createContextAsUser(userHandle, 0).getPackageManager().getPackageInfo(str, i);
            } catch (PackageManager.NameNotFoundException unused) {
                packageInfo = null;
            }
            arrayList.add(new UserPackage(userHandle, packageInfo));
        }
        return arrayList;
    }

    public boolean isEnabledPackage() {
        PackageInfo packageInfo = this.mPackageInfo;
        if (packageInfo == null) {
            return false;
        }
        return packageInfo.applicationInfo.enabled;
    }

    public boolean isInstalledPackage() {
        PackageInfo packageInfo = this.mPackageInfo;
        return (packageInfo == null || (packageInfo.applicationInfo.flags & 8388608) == 0 || (this.mPackageInfo.applicationInfo.privateFlags & 1) != 0) ? false : true;
    }

    public UserHandle getUser() {
        return this.mUser;
    }

    public PackageInfo getPackageInfo() {
        return this.mPackageInfo;
    }
}
