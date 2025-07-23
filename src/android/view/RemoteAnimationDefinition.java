package android.view;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.ArraySet;
import android.util.Slog;
import android.util.SparseArray;

/* loaded from: classes4.dex */
public class RemoteAnimationDefinition implements Parcelable {
    public static final Parcelable.Creator<RemoteAnimationDefinition> CREATOR = new Parcelable.Creator<RemoteAnimationDefinition>() { // from class: android.view.RemoteAnimationDefinition.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RemoteAnimationDefinition createFromParcel(Parcel parcel) {
            return new RemoteAnimationDefinition(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RemoteAnimationDefinition[] newArray(int i) {
            return new RemoteAnimationDefinition[i];
        }
    };
    private final SparseArray<RemoteAnimationAdapterEntry> mTransitionAnimationMap;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public RemoteAnimationDefinition() {
        this.mTransitionAnimationMap = new SparseArray<>();
    }

    public void addRemoteAnimation(int i, int i2, RemoteAnimationAdapter remoteAnimationAdapter) {
        this.mTransitionAnimationMap.put(i, new RemoteAnimationAdapterEntry(remoteAnimationAdapter, i2));
    }

    public void addRemoteAnimation(int i, RemoteAnimationAdapter remoteAnimationAdapter) {
        addRemoteAnimation(i, 0, remoteAnimationAdapter);
    }

    public boolean hasTransition(int i, ArraySet<Integer> arraySet) {
        return getAdapter(i, arraySet) != null;
    }

    public RemoteAnimationAdapter getAdapter(int i, ArraySet<Integer> arraySet) {
        RemoteAnimationAdapterEntry remoteAnimationAdapterEntry = this.mTransitionAnimationMap.get(i);
        if (remoteAnimationAdapterEntry == null) {
            return null;
        }
        if (remoteAnimationAdapterEntry.activityTypeFilter == 0 || arraySet.contains(Integer.valueOf(remoteAnimationAdapterEntry.activityTypeFilter))) {
            return remoteAnimationAdapterEntry.adapter;
        }
        return null;
    }

    public RemoteAnimationDefinition(Parcel parcel) {
        int readInt = parcel.readInt();
        this.mTransitionAnimationMap = new SparseArray<>(readInt);
        for (int i = 0; i < readInt; i++) {
            this.mTransitionAnimationMap.put(parcel.readInt(), (RemoteAnimationAdapterEntry) parcel.readTypedObject(RemoteAnimationAdapterEntry.CREATOR));
        }
    }

    public void setCallingPidUid(int i, int i2) {
        for (int size = this.mTransitionAnimationMap.size() - 1; size >= 0; size--) {
            this.mTransitionAnimationMap.valueAt(size).adapter.setCallingPidUid(i, i2);
        }
    }

    public void linkToDeath(IBinder.DeathRecipient deathRecipient) {
        for (int i = 0; i < this.mTransitionAnimationMap.size(); i++) {
            try {
                this.mTransitionAnimationMap.valueAt(i).adapter.getRunner().asBinder().linkToDeath(deathRecipient, 0);
            } catch (RemoteException unused) {
                Slog.e("RemoteAnimationDefinition", "Failed to link to death recipient");
                return;
            }
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        int size = this.mTransitionAnimationMap.size();
        parcel.writeInt(size);
        for (int i2 = 0; i2 < size; i2++) {
            parcel.writeInt(this.mTransitionAnimationMap.keyAt(i2));
            parcel.writeTypedObject(this.mTransitionAnimationMap.valueAt(i2), i);
        }
    }

    private static class RemoteAnimationAdapterEntry implements Parcelable {
        public static final Parcelable.Creator<RemoteAnimationAdapterEntry> CREATOR = new Parcelable.Creator<RemoteAnimationAdapterEntry>() { // from class: android.view.RemoteAnimationDefinition.RemoteAnimationAdapterEntry.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public RemoteAnimationAdapterEntry createFromParcel(Parcel parcel) {
                return new RemoteAnimationAdapterEntry(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public RemoteAnimationAdapterEntry[] newArray(int i) {
                return new RemoteAnimationAdapterEntry[i];
            }
        };
        final int activityTypeFilter;
        final RemoteAnimationAdapter adapter;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        RemoteAnimationAdapterEntry(RemoteAnimationAdapter remoteAnimationAdapter, int i) {
            this.adapter = remoteAnimationAdapter;
            this.activityTypeFilter = i;
        }

        private RemoteAnimationAdapterEntry(Parcel parcel) {
            this.adapter = (RemoteAnimationAdapter) parcel.readTypedObject(RemoteAnimationAdapter.CREATOR);
            this.activityTypeFilter = parcel.readInt();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeTypedObject(this.adapter, i);
            parcel.writeInt(this.activityTypeFilter);
        }
    }
}
