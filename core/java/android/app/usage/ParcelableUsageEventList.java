package android.app.usage;

import android.app.usage.UsageEvents;
import android.content.res.Configuration;
import android.os.BadParcelableException;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public final class ParcelableUsageEventList implements Parcelable {
    private static final boolean DEBUG = false;
    private static final boolean DEBUG_ALL = false;
    private static final String TAG = "ParcelableUsageEventList";
    private List<UsageEvents.Event> mList;
    private static final int MAX_IPC_SIZE = IBinder.getSuggestedMaxIpcSizeBytes();
    public static final Parcelable.Creator<ParcelableUsageEventList> CREATOR = new Parcelable.Creator<ParcelableUsageEventList>() { // from class: android.app.usage.ParcelableUsageEventList.2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ParcelableUsageEventList createFromParcel(Parcel in) {
            return new ParcelableUsageEventList(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ParcelableUsageEventList[] newArray(int size) {
            return new ParcelableUsageEventList[size];
        }
    };

    public ParcelableUsageEventList(List<UsageEvents.Event> list) {
        if (list == null) {
            throw new IllegalArgumentException("Empty list");
        }
        this.mList = list;
    }

    private ParcelableUsageEventList(Parcel in) {
        int N = in.readInt();
        this.mList = new ArrayList(N);
        if (N <= 0) {
            return;
        }
        int i = 0;
        while (i < N && in.readInt() != 0) {
            this.mList.add(readEventFromParcel(in));
            i++;
        }
        if (i >= N) {
            return;
        }
        IBinder retriever = in.readStrongBinder();
        while (i < N) {
            Parcel data = Parcel.obtain();
            Parcel reply = Parcel.obtain();
            data.writeInt(i);
            try {
                try {
                    retriever.transact(1, data, reply, 0);
                    reply.readException();
                    int count = 0;
                    while (i < N && reply.readInt() != 0) {
                        this.mList.add(readEventFromParcel(reply));
                        i++;
                        count++;
                    }
                } catch (RemoteException e) {
                    throw new BadParcelableException("Failure retrieving array; only received " + i + " of " + N, e);
                }
            } finally {
                reply.recycle();
                data.recycle();
            }
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, final int flags) {
        final int N = this.mList.size();
        dest.writeInt(N);
        if (N > 0) {
            int i = 0;
            while (i < N && dest.dataSize() < MAX_IPC_SIZE) {
                dest.writeInt(1);
                UsageEvents.Event event = this.mList.get(i);
                writeEventToParcel(event, dest, flags);
                i++;
            }
            if (i < N) {
                dest.writeInt(0);
                Binder retriever = new Binder() { // from class: android.app.usage.ParcelableUsageEventList.1
                    @Override // android.os.Binder
                    protected boolean onTransact(int code, Parcel data, Parcel reply, int flags2) throws RemoteException {
                        if (code != 1) {
                            return super.onTransact(code, data, reply, flags2);
                        }
                        if (ParcelableUsageEventList.this.mList == null) {
                            throw new IllegalArgumentException("Attempt to transfer null list, did transfer finish?");
                        }
                        int i2 = data.readInt();
                        try {
                            reply.writeNoException();
                            int count = 0;
                            while (i2 < N && reply.dataSize() < 65536) {
                                reply.writeInt(1);
                                UsageEvents.Event event2 = (UsageEvents.Event) ParcelableUsageEventList.this.mList.get(i2);
                                ParcelableUsageEventList.this.writeEventToParcel(event2, reply, flags);
                                i2++;
                                count++;
                            }
                            if (i2 >= N) {
                                ParcelableUsageEventList.this.mList = null;
                            } else {
                                reply.writeInt(0);
                            }
                            return true;
                        } catch (RuntimeException e) {
                            ParcelableUsageEventList.this.mList = null;
                            throw e;
                        }
                    }
                };
                dest.writeStrongBinder(retriever);
            }
        }
    }

    public List<UsageEvents.Event> getList() {
        return this.mList;
    }

    private UsageEvents.Event readEventFromParcel(Parcel in) {
        UsageEvents.Event event = new UsageEvents.Event();
        event.mPackage = in.readString();
        event.mClass = in.readString();
        event.mInstanceId = in.readInt();
        event.mTaskRootPackage = in.readString();
        event.mTaskRootClass = in.readString();
        event.mEventType = in.readInt();
        event.mTimeStamp = in.readLong();
        event.mConfiguration = null;
        event.mShortcutId = null;
        event.mAction = null;
        event.mContentType = null;
        event.mContentAnnotations = null;
        event.mNotificationChannelId = null;
        event.mLocusId = null;
        event.mExtras = null;
        switch (event.mEventType) {
            case 5:
                event.mConfiguration = Configuration.CREATOR.createFromParcel(in);
                break;
            case 7:
                if (in.readInt() != 0) {
                    event.mExtras = in.readPersistableBundle(getClass().getClassLoader());
                    break;
                }
                break;
            case 8:
                event.mShortcutId = in.readString();
                break;
            case 9:
                event.mAction = in.readString();
                event.mContentType = in.readString();
                event.mContentAnnotations = in.readStringArray();
                break;
            case 11:
                event.mBucketAndReason = in.readInt();
                break;
            case 12:
                event.mNotificationChannelId = in.readString();
                break;
            case 30:
                event.mLocusId = in.readString();
                break;
        }
        event.mFlags = in.readInt();
        return event;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void writeEventToParcel(UsageEvents.Event event, Parcel dest, int flags) {
        dest.writeString(event.mPackage);
        dest.writeString(event.mClass);
        dest.writeInt(event.mInstanceId);
        dest.writeString(event.mTaskRootPackage);
        dest.writeString(event.mTaskRootClass);
        dest.writeInt(event.mEventType);
        dest.writeLong(event.mTimeStamp);
        switch (event.mEventType) {
            case 5:
                event.mConfiguration.writeToParcel(dest, flags);
                break;
            case 7:
                if (event.mExtras != null) {
                    dest.writeInt(1);
                    dest.writePersistableBundle(event.mExtras);
                    break;
                } else {
                    dest.writeInt(0);
                    break;
                }
            case 8:
                dest.writeString(event.mShortcutId);
                break;
            case 9:
                dest.writeString(event.mAction);
                dest.writeString(event.mContentType);
                dest.writeStringArray(event.mContentAnnotations);
                break;
            case 11:
                dest.writeInt(event.mBucketAndReason);
                break;
            case 12:
                dest.writeString(event.mNotificationChannelId);
                break;
            case 30:
                dest.writeString(event.mLocusId);
                break;
        }
        dest.writeInt(event.mFlags);
    }
}
