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
            Parcel parcelObtain = Parcel.obtain();
            try {
                try {
                    this.mRankingMapFd = (SharedMemory) parcel.readParcelable(getClass().getClassLoader(), SharedMemory.class);
                    Bundle bundle = parcel.readBundle(getClass().getClassLoader());
                    SharedMemory sharedMemory = this.mRankingMapFd;
                    if (sharedMemory == null) {
                        this.mRankingMap = null;
                        parcelObtain.recycle();
                        return;
                    }
                    ByteBuffer byteBufferMapReadOnly = sharedMemory.mapReadOnly();
                    int iRemaining = byteBufferMapReadOnly.remaining();
                    byte[] bArr = new byte[iRemaining];
                    byteBufferMapReadOnly.get(bArr);
                    parcelObtain.unmarshall(bArr, 0, iRemaining);
                    parcelObtain.setDataPosition(0);
                    this.mRankingMap = (NotificationListenerService.RankingMap) parcelObtain.readParcelable(getClass().getClassLoader(), NotificationListenerService.RankingMap.class);
                    addSmartActionsFromBundleToRankingMap(bundle);
                    parcelObtain.recycle();
                    if (byteBufferMapReadOnly == null || this.mRankingMapFd == null) {
                        return;
                    }
                    SharedMemory.unmap(byteBufferMapReadOnly);
                    this.mRankingMapFd.close();
                    return;
                } catch (ErrnoException e) {
                    throw new RuntimeException(e);
                }
            } catch (Throwable th) {
                parcelObtain.recycle();
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
        ByteBuffer byteBufferMapReadWrite;
        SharedMemory sharedMemory;
        if (Flags.rankingUpdateAshmem()) {
            Parcel parcelObtain = Parcel.obtain();
            ArrayList arrayList = new ArrayList();
            Bundle bundle = new Bundle();
            String[] orderedKeys = this.mRankingMap.getOrderedKeys();
            int i2 = 0;
            while (true) {
                byteBufferMapReadWrite = null;
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
                    parcelObtain.writeParcelable(new NotificationListenerService.RankingMap((NotificationListenerService.Ranking[]) arrayList.toArray(new NotificationListenerService.Ranking[0])), i);
                    SharedMemory sharedMemoryCreate = SharedMemory.create("NotificationRankingUpdatedSharedMemory", parcelObtain.dataSize());
                    this.mRankingMapFd = sharedMemoryCreate;
                    byteBufferMapReadWrite = sharedMemoryCreate.mapReadWrite();
                    parcelObtain.marshall(byteBufferMapReadWrite);
                    this.mRankingMapFd.setProtect(OsConstants.PROT_READ);
                    parcel.writeParcelable(this.mRankingMapFd, i);
                    parcel.writeBundle(bundle);
                    if (byteBufferMapReadWrite != null) {
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
                parcelObtain.recycle();
                if (byteBufferMapReadWrite != null && this.mRankingMapFd != null) {
                    SharedMemory.unmap(byteBufferMapReadWrite);
                    this.mRankingMapFd.close();
                }
            }
        }
        parcel.writeParcelable(this.mRankingMap, i);
    }
}
