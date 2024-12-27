package android.service.notification;

import android.app.Notification;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SharedMemory;
import android.system.ErrnoException;
import android.system.OsConstants;

import com.android.internal.hidden_from_bootclasspath.android.service.notification.Flags;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class NotificationRankingUpdate implements Parcelable {
    public static final Parcelable.Creator<NotificationRankingUpdate> CREATOR =
            new Parcelable.Creator<
                    NotificationRankingUpdate>() { // from class:
                                                   // android.service.notification.NotificationRankingUpdate.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public NotificationRankingUpdate createFromParcel(Parcel parcel) {
                    return new NotificationRankingUpdate(parcel);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public NotificationRankingUpdate[] newArray(int size) {
                    return new NotificationRankingUpdate[size];
                }
            };
    private final NotificationListenerService.RankingMap mRankingMap;
    private SharedMemory mRankingMapFd;
    private final String mSharedMemoryName;

    public NotificationRankingUpdate(NotificationListenerService.Ranking[] rankings) {
        this.mRankingMapFd = null;
        this.mSharedMemoryName = "NotificationRankingUpdatedSharedMemory";
        this.mRankingMap = new NotificationListenerService.RankingMap(rankings);
    }

    public NotificationRankingUpdate(Parcel in) {
        SharedMemory sharedMemory;
        this.mRankingMapFd = null;
        this.mSharedMemoryName = "NotificationRankingUpdatedSharedMemory";
        if (!Flags.rankingUpdateAshmem()) {
            this.mRankingMap =
                    (NotificationListenerService.RankingMap)
                            in.readParcelable(
                                    getClass().getClassLoader(),
                                    NotificationListenerService.RankingMap.class);
            return;
        }
        Parcel mapParcel = Parcel.obtain();
        try {
            try {
                this.mRankingMapFd =
                        (SharedMemory)
                                in.readParcelable(getClass().getClassLoader(), SharedMemory.class);
                Bundle smartActionsBundle = in.readBundle(getClass().getClassLoader());
                if (this.mRankingMapFd == null) {
                    this.mRankingMap = null;
                    if (buffer != null) {
                        if (sharedMemory != null) {
                            return;
                        } else {
                            return;
                        }
                    }
                    return;
                }
                ByteBuffer buffer = this.mRankingMapFd.mapReadOnly();
                byte[] payload = new byte[buffer.remaining()];
                buffer.get(payload);
                mapParcel.unmarshall(payload, 0, payload.length);
                mapParcel.setDataPosition(0);
                this.mRankingMap =
                        (NotificationListenerService.RankingMap)
                                mapParcel.readParcelable(
                                        getClass().getClassLoader(),
                                        NotificationListenerService.RankingMap.class);
                addSmartActionsFromBundleToRankingMap(smartActionsBundle);
                mapParcel.recycle();
                if (buffer == null || this.mRankingMapFd == null) {
                    return;
                }
                SharedMemory.unmap(buffer);
                this.mRankingMapFd.close();
            } catch (ErrnoException e) {
                throw new RuntimeException(e);
            }
        } finally {
            mapParcel.recycle();
            if (0 != 0 && this.mRankingMapFd != null) {
                SharedMemory.unmap(null);
                this.mRankingMapFd.close();
            }
        }
    }

    private void addSmartActionsFromBundleToRankingMap(Bundle smartActionsBundle) {
        if (smartActionsBundle == null) {
            return;
        }
        String[] rankingMapKeys = this.mRankingMap.getOrderedKeys();
        for (String key : rankingMapKeys) {
            ArrayList<Notification.Action> smartActions =
                    smartActionsBundle.getParcelableArrayList(key, Notification.Action.class);
            NotificationListenerService.Ranking ranking = this.mRankingMap.getRawRankingObject(key);
            ranking.setSmartActions(smartActions);
        }
    }

    public final boolean isFdNotNullAndClosed() {
        return this.mRankingMapFd != null && this.mRankingMapFd.getFd() == -1;
    }

    public NotificationListenerService.RankingMap getRankingMap() {
        return this.mRankingMap;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NotificationRankingUpdate other = (NotificationRankingUpdate) o;
        return this.mRankingMap.equals(other.mRankingMap);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        SharedMemory sharedMemory;
        if (Flags.rankingUpdateAshmem()) {
            Parcel mapParcel = Parcel.obtain();
            ArrayList<NotificationListenerService.Ranking> marshalableRankings = new ArrayList<>();
            Bundle smartActionsBundle = new Bundle();
            String[] rankingMapKeys = this.mRankingMap.getOrderedKeys();
            for (String key : rankingMapKeys) {
                NotificationListenerService.Ranking ranking =
                        this.mRankingMap.getRawRankingObject(key);
                List<Notification.Action> smartActions = ranking.getSmartActions();
                if (!smartActions.isEmpty()) {
                    smartActionsBundle.putParcelableList(key, smartActions);
                }
                NotificationListenerService.Ranking rankingCopy =
                        new NotificationListenerService.Ranking();
                rankingCopy.populate(ranking);
                rankingCopy.setSmartActions(null);
                marshalableRankings.add(rankingCopy);
            }
            NotificationListenerService.RankingMap marshalableRankingMap =
                    new NotificationListenerService.RankingMap(
                            (NotificationListenerService.Ranking[])
                                    marshalableRankings.toArray(
                                            new NotificationListenerService.Ranking[0]));
            ByteBuffer buffer = null;
            try {
                try {
                    mapParcel.writeParcelable(marshalableRankingMap, flags);
                    int mapSize = mapParcel.dataSize();
                    this.mRankingMapFd =
                            SharedMemory.create("NotificationRankingUpdatedSharedMemory", mapSize);
                    buffer = this.mRankingMapFd.mapReadWrite();
                    buffer.put(mapParcel.marshall(), 0, mapSize);
                    this.mRankingMapFd.setProtect(OsConstants.PROT_READ);
                    out.writeParcelable(this.mRankingMapFd, flags);
                    out.writeBundle(smartActionsBundle);
                    if (buffer != null) {
                        if (sharedMemory != null) {
                            return;
                        } else {
                            return;
                        }
                    }
                    return;
                } catch (ErrnoException e) {
                    throw new RuntimeException(e);
                }
            } finally {
                mapParcel.recycle();
                if (buffer != null && this.mRankingMapFd != null) {
                    SharedMemory.unmap(buffer);
                    this.mRankingMapFd.close();
                }
            }
        }
        out.writeParcelable(this.mRankingMap, flags);
    }
}
