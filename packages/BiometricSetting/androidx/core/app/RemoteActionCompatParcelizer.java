package androidx.core.app;

import android.app.PendingIntent;
import androidx.core.graphics.drawable.IconCompat;
import androidx.versionedparcelable.VersionedParcel;

/* loaded from: classes.dex */
public class RemoteActionCompatParcelizer {
    public static RemoteActionCompat read(VersionedParcel versionedParcel) {
        RemoteActionCompat remoteActionCompat = new RemoteActionCompat();
        remoteActionCompat.mIcon = (IconCompat) versionedParcel.readVersionedParcelable(remoteActionCompat.mIcon);
        remoteActionCompat.mTitle = versionedParcel.readCharSequence(2, remoteActionCompat.mTitle);
        remoteActionCompat.mContentDescription = versionedParcel.readCharSequence(3, remoteActionCompat.mContentDescription);
        remoteActionCompat.mActionIntent = (PendingIntent) versionedParcel.readParcelable(remoteActionCompat.mActionIntent, 4);
        remoteActionCompat.mEnabled = versionedParcel.readBoolean(5, remoteActionCompat.mEnabled);
        remoteActionCompat.mShouldShowIcon = versionedParcel.readBoolean(6, remoteActionCompat.mShouldShowIcon);
        return remoteActionCompat;
    }

    public static void write(RemoteActionCompat remoteActionCompat, VersionedParcel versionedParcel) {
        versionedParcel.getClass();
        versionedParcel.writeVersionedParcelable(remoteActionCompat.mIcon);
        versionedParcel.writeCharSequence(2, remoteActionCompat.mTitle);
        versionedParcel.writeCharSequence(3, remoteActionCompat.mContentDescription);
        versionedParcel.writeParcelable(remoteActionCompat.mActionIntent, 4);
        versionedParcel.writeBoolean(5, remoteActionCompat.mEnabled);
        versionedParcel.writeBoolean(6, remoteActionCompat.mShouldShowIcon);
    }
}
