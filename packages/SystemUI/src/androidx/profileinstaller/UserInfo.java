package androidx.profileinstaller;

import android.os.Parcel;
import android.os.Process;
import android.util.Log;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class UserInfo {
    private UserInfo() {
    }

    public static int getCurrentUserId() {
        try {
            Parcel obtain = Parcel.obtain();
            Process.myUserHandle().writeToParcel(obtain, 0);
            obtain.setDataPosition(0);
            return obtain.readInt();
        } catch (Throwable unused) {
            Log.d("ProfileInstaller", "Error when reading current user id. Selected default user id `0`.");
            return 0;
        }
    }
}
