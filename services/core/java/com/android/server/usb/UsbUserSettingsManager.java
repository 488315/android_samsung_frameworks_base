package com.android.server.usb;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.hardware.usb.AccessoryFilter;
import android.hardware.usb.DeviceFilter;
import android.os.UserHandle;
import android.util.sysfwutil.Slog;
import com.android.internal.util.dump.DualDumpOutputStream;
import com.android.internal.util.dump.DumpUtils;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class UsbUserSettingsManager {
    public final Object mLock = new Object();
    public final PackageManager mPackageManager;
    public final UserHandle mUser;

    public UsbUserSettingsManager(Context context, UserHandle userHandle) {
        Slog.v("UsbUserSettingsManager", "Creating settings for " + userHandle);
        try {
            this.mPackageManager = context.createPackageContextAsUser("android", 0, userHandle).getPackageManager();
            this.mUser = userHandle;
        } catch (PackageManager.NameNotFoundException unused) {
            throw new RuntimeException("Missing android package");
        }
    }

    public final void dump(DualDumpOutputStream dualDumpOutputStream) {
        long start = dualDumpOutputStream.start("user_settings", 2246267895809L);
        synchronized (this.mLock) {
            try {
                dualDumpOutputStream.write("user_id", 1120986464257L, this.mUser.getIdentifier());
                List queryIntentActivitiesAsUser = this.mPackageManager.queryIntentActivitiesAsUser(new Intent("android.hardware.usb.action.USB_DEVICE_ATTACHED"), 128, this.mUser.getIdentifier());
                int size = queryIntentActivitiesAsUser.size();
                int i = 0;
                while (i < size) {
                    ResolveInfo resolveInfo = (ResolveInfo) queryIntentActivitiesAsUser.get(i);
                    long start2 = dualDumpOutputStream.start("device_attached_activities", 2246267895812L);
                    ActivityInfo activityInfo = resolveInfo.activityInfo;
                    DumpUtils.writeComponentName(dualDumpOutputStream, "activity", 1146756268033L, new ComponentName(activityInfo.packageName, activityInfo.name));
                    ArrayList deviceFilters = UsbProfileGroupSettingsManager.getDeviceFilters(this.mPackageManager, resolveInfo);
                    if (deviceFilters != null) {
                        int size2 = deviceFilters.size();
                        int i2 = 0;
                        while (i2 < size2) {
                            ((DeviceFilter) deviceFilters.get(i2)).dump(dualDumpOutputStream, "filters", 2246267895810L);
                            i2++;
                            queryIntentActivitiesAsUser = queryIntentActivitiesAsUser;
                            size = size;
                        }
                    }
                    dualDumpOutputStream.end(start2);
                    i++;
                    queryIntentActivitiesAsUser = queryIntentActivitiesAsUser;
                    size = size;
                }
                List queryIntentActivitiesAsUser2 = this.mPackageManager.queryIntentActivitiesAsUser(new Intent("android.hardware.usb.action.USB_ACCESSORY_ATTACHED"), 128, this.mUser.getIdentifier());
                int size3 = queryIntentActivitiesAsUser2.size();
                int i3 = 0;
                while (i3 < size3) {
                    ResolveInfo resolveInfo2 = (ResolveInfo) queryIntentActivitiesAsUser2.get(i3);
                    long start3 = dualDumpOutputStream.start("accessory_attached_activities", 2246267895813L);
                    ActivityInfo activityInfo2 = resolveInfo2.activityInfo;
                    DumpUtils.writeComponentName(dualDumpOutputStream, "activity", 1146756268033L, new ComponentName(activityInfo2.packageName, activityInfo2.name));
                    ArrayList accessoryFilters = UsbProfileGroupSettingsManager.getAccessoryFilters(this.mPackageManager, resolveInfo2);
                    if (accessoryFilters != null) {
                        int size4 = accessoryFilters.size();
                        int i4 = 0;
                        while (i4 < size4) {
                            ((AccessoryFilter) accessoryFilters.get(i4)).dump(dualDumpOutputStream, "filters", 2246267895810L);
                            i4++;
                            queryIntentActivitiesAsUser2 = queryIntentActivitiesAsUser2;
                            size3 = size3;
                        }
                    }
                    dualDumpOutputStream.end(start3);
                    i3++;
                    queryIntentActivitiesAsUser2 = queryIntentActivitiesAsUser2;
                    size3 = size3;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        dualDumpOutputStream.end(start);
    }
}
