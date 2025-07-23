package android.service.notification;

import android.app.Notification;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SharedMemory;
import android.service.notification.NotificationListenerService;
import android.system.ErrnoException;
import android.system.OsConstants;
import com.android.internal.hidden_from_bootclasspath.android.service.notification.Flags;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class NotificationRankingUpdate implements Parcelable {
    public static final Parcelable.Creator<NotificationRankingUpdate> CREATOR = new Parcelable.Creator<NotificationRankingUpdate>() { // from class: android.service.notification.NotificationRankingUpdate.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NotificationRankingUpdate createFromParcel(Parcel parcel) {
            return new NotificationRankingUpdate(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NotificationRankingUpdate[] newArray(int i) {
            return new NotificationRankingUpdate[i];
        }
    };
    private final NotificationListenerService.RankingMap mRankingMap;
    private SharedMemory mRankingMapFd;
    private final String mSharedMemoryName;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public NotificationRankingUpdate(NotificationListenerService.Ranking[] rankingArr) {
        this.mRankingMapFd = null;
        this.mSharedMemoryName = "NotificationRankingUpdatedSharedMemory";
        this.mRankingMap = new NotificationListenerService.RankingMap(rankingArr);
    }

    public NotificationRankingUpdate(Parcel parcel) {
        this.mRankingMapFd = null;
        this.mSharedMemoryName = "NotificationRankingUpdatedSharedMemory";
        if (Flags.rankingUpdateAshmem()) {
            Parcel obtain = Parcel.obtain();
            try {
                try {
                    this.mRankingMapFd = (SharedMemory) parcel.readParcelable(getClass().getClassLoader(), SharedMemory.class);
                    Bundle readBundle = parcel.readBundle(getClass().getClassLoader());
                    SharedMemory sharedMemory = this.mRankingMapFd;
                    if (sharedMemory == null) {
                        this.mRankingMap = null;
                        obtain.recycle();
                        return;
                    }
                    ByteBuffer mapReadOnly = sharedMemory.mapReadOnly();
                    int remaining = mapReadOnly.remaining();
                    byte[] bArr = new byte[remaining];
                    mapReadOnly.get(bArr);
                    obtain.unmarshall(bArr, 0, remaining);
                    obtain.setDataPosition(0);
                    this.mRankingMap = (NotificationListenerService.RankingMap) obtain.readParcelable(getClass().getClassLoader(), NotificationListenerService.RankingMap.class);
                    addSmartActionsFromBundleToRankingMap(readBundle);
                    obtain.recycle();
                    if (mapReadOnly == null || this.mRankingMapFd == null) {
                        return;
                    }
                    SharedMemory.unmap(mapReadOnly);
                    this.mRankingMapFd.close();
                    return;
                } catch (ErrnoException e) {
                    throw new RuntimeException(e);
                }
            } catch (Throwable th) {
                obtain.recycle();
                if (0 != 0 && this.mRankingMapFd != null) {
                    SharedMemory.unmap(null);
                    this.mRankingMapFd.close();
                }
                throw th;
            }
        }
        this.mRankingMap = (NotificationListenerService.RankingMap) parcel.readParcelable(getClass().getClassLoader(), NotificationListenerService.RankingMap.class);
    }

    private void addSmartActionsFromBundleToRankingMap(Bundle bundle) {
        if (bundle == null) {
            return;
        }
        for (String str : this.mRankingMap.getOrderedKeys()) {
            this.mRankingMap.getRawRankingObject(str).setSmartActions(bundle.getParcelableArrayList(str, Notification.Action.class));
        }
    }

    public final boolean isFdNotNullAndClosed() {
        SharedMemory sharedMemory = this.mRankingMapFd;
        return sharedMemory != null && sharedMemory.getFd() == -1;
    }

    public NotificationListenerService.RankingMap getRankingMap() {
        return this.mRankingMap;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return this.mRankingMap.equals(((NotificationRankingUpdate) obj).mRankingMap);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        ByteBuffer byteBuffer;
        SharedMemory sharedMemory;
        if (Flags.rankingUpdateAshmem()) {
            Parcel obtain = Parcel.obtain();
            ArrayList arrayList = new ArrayList();
            Bundle bundle = new Bundle();
            String[] orderedKeys = this.mRankingMap.getOrderedKeys();
            int i2 = 0;
            while (true) {
                byteBuffer = null;
                if (i2 >= orderedKeys.length) {
                    break;
                }
                String str = orderedKeys[i2];
                NotificationListenerService.Ranking rawRankingObject = this.mRankingMap.getRawRankingObject(str);
                List<Notification.Action> smartActions = rawRankingObject.getSmartActions();
                if (!smartActions.isEmpty()) {
                    bundle.putParcelableList(str, smartActions);
                }
                NotificationListenerService.Ranking ranking = new NotificationListenerService.Ranking();
                ranking.populate(rawRankingObject);
                ranking.setSmartActions(null);
                arrayList.add(ranking);
                i2++;
            }
            try {
                try {
                    obtain.writeParcelable(new NotificationListenerService.RankingMap((NotificationListenerService.Ranking[]) arrayList.toArray(new NotificationListenerService.Ranking[0])), i);
                    SharedMemory create = SharedMemory.create("NotificationRankingUpdatedSharedMemory", obtain.dataSize());
                    this.mRankingMapFd = create;
                    byteBuffer = create.mapReadWrite();
                    obtain.marshall(byteBuffer);
                    this.mRankingMapFd.setProtect(OsConstants.PROT_READ);
                    parcel.writeParcelable(this.mRankingMapFd, i);
                    parcel.writeBundle(bundle);
                    if (byteBuffer != null) {
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
                obtain.recycle();
                if (byteBuffer != null && this.mRankingMapFd != null) {
                    SharedMemory.unmap(byteBuffer);
                    this.mRankingMapFd.close();
                }
            }
        }
        parcel.writeParcelable(this.mRankingMap, i);
    }
}
