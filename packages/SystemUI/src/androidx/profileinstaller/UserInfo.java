package androidx.profileinstaller;

import android.os.Parcel;
import android.os.Process;
import android.util.Log;

/* loaded from: classes.dex */
public class UserInfo {
    private UserInfo() {
    }

    public static int getCurrentUserId() {
        try {
            Parcel parcelObtain = Parcel.obtain();
            Process.myUserHandle().writeToParcel(parcelObtain, 0);
            parcelObtain.setDataPosition(0);
            return parcelObtain.readInt();
        } catch (Throwable unused) {
            Log.d("ProfileInstaller", "Error when reading current user id. Selected default user id `0`.");
            return 0;
        }
    }
}
