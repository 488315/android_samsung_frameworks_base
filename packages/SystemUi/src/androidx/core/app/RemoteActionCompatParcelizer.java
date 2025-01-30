package androidx.core.app;

import android.app.PendingIntent;
import androidx.core.graphics.drawable.IconCompat;
import androidx.versionedparcelable.VersionedParcel;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class RemoteActionCompatParcelizer {
    public static RemoteActionCompat read(VersionedParcel versionedParcel) {
        RemoteActionCompat remoteActionCompat = new RemoteActionCompat();
        remoteActionCompat.mIcon = (IconCompat) versionedParcel.readVersionedParcelable(remoteActionCompat.mIcon, 1);
        CharSequence charSequence = remoteActionCompat.mTitle;
        if (versionedParcel.readField(2)) {
            charSequence = versionedParcel.readCharSequence();
        }
        remoteActionCompat.mTitle = charSequence;
        CharSequence charSequence2 = remoteActionCompat.mContentDescription;
        if (versionedParcel.readField(3)) {
            charSequence2 = versionedParcel.readCharSequence();
        }
        remoteActionCompat.mContentDescription = charSequence2;
        remoteActionCompat.mActionIntent = (PendingIntent) versionedParcel.readParcelable(remoteActionCompat.mActionIntent, 4);
        boolean z = remoteActionCompat.mEnabled;
        if (versionedParcel.readField(5)) {
            z = versionedParcel.readBoolean();
        }
        remoteActionCompat.mEnabled = z;
        boolean z2 = remoteActionCompat.mShouldShowIcon;
        if (versionedParcel.readField(6)) {
            z2 = versionedParcel.readBoolean();
        }
        remoteActionCompat.mShouldShowIcon = z2;
        return remoteActionCompat;
    }

    public static void write(RemoteActionCompat remoteActionCompat, VersionedParcel versionedParcel) {
        versionedParcel.getClass();
        IconCompat iconCompat = remoteActionCompat.mIcon;
        versionedParcel.setOutputField(1);
        versionedParcel.writeVersionedParcelable(iconCompat);
        CharSequence charSequence = remoteActionCompat.mTitle;
        versionedParcel.setOutputField(2);
        versionedParcel.writeCharSequence(charSequence);
        CharSequence charSequence2 = remoteActionCompat.mContentDescription;
        versionedParcel.setOutputField(3);
        versionedParcel.writeCharSequence(charSequence2);
        versionedParcel.writeParcelable(remoteActionCompat.mActionIntent, 4);
        boolean z = remoteActionCompat.mEnabled;
        versionedParcel.setOutputField(5);
        versionedParcel.writeBoolean(z);
        boolean z2 = remoteActionCompat.mShouldShowIcon;
        versionedParcel.setOutputField(6);
        versionedParcel.writeBoolean(z2);
    }
}
