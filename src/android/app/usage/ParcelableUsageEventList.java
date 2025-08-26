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
        public ParcelableUsageEventList createFromParcel(Parcel parcel) {
            return new ParcelableUsageEventList(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ParcelableUsageEventList[] newArray(int i) {
            return new ParcelableUsageEventList[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ParcelableUsageEventList(List<UsageEvents.Event> list) {
        if (list == null) {
            throw new IllegalArgumentException("Empty list");
        }
        this.mList = list;
    }

    private ParcelableUsageEventList(Parcel parcel) {
        int i = parcel.readInt();
        this.mList = new ArrayList(i);
        if (i <= 0) {
            return;
        }
        int i2 = 0;
        while (i2 < i && parcel.readInt() != 0) {
            this.mList.add(readEventFromParcel(parcel));
            i2++;
        }
        if (i2 >= i) {
            return;
        }
        IBinder strongBinder = parcel.readStrongBinder();
        while (i2 < i) {
            Parcel parcelObtain = Parcel.obtain();
            Parcel parcelObtain2 = Parcel.obtain();
            parcelObtain.writeInt(i2);
            try {
                try {
                    strongBinder.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    while (i2 < i && parcelObtain2.readInt() != 0) {
                        this.mList.add(readEventFromParcel(parcelObtain2));
                        i2++;
                    }
                } catch (RemoteException e) {
                    throw new BadParcelableException("Failure retrieving array; only received " + i2 + " of " + i, e);
                }
            } finally {
                parcelObtain2.recycle();
                parcelObtain.recycle();
            }
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, final int i) {
        final int size = this.mList.size();
        parcel.writeInt(size);
        if (size > 0) {
            int i2 = 0;
            while (i2 < size && parcel.dataSize() < MAX_IPC_SIZE) {
                parcel.writeInt(1);
                writeEventToParcel(this.mList.get(i2), parcel, i);
                i2++;
            }
            if (i2 < size) {
                parcel.writeInt(0);
                parcel.writeStrongBinder(new Binder() { // from class: android.app.usage.ParcelableUsageEventList.1
                    @Override // android.os.Binder
                    protected boolean onTransact(int i3, Parcel parcel2, Parcel parcel3, int i4) throws RemoteException {
                        if (i3 != 1) {
                            return super.onTransact(i3, parcel2, parcel3, i4);
                        }
                        if (ParcelableUsageEventList.this.mList == null) {
                            throw new IllegalArgumentException("Attempt to transfer null list, did transfer finish?");
                        }
                        int i5 = parcel2.readInt();
                        try {
                            parcel3.writeNoException();
                            while (i5 < size && parcel3.dataSize() < 65536) {
                                parcel3.writeInt(1);
                                ParcelableUsageEventList.this.writeEventToParcel((UsageEvents.Event) ParcelableUsageEventList.this.mList.get(i5), parcel3, i);
                                i5++;
                            }
                            if (i5 < size) {
                                parcel3.writeInt(0);
                            } else {
                                ParcelableUsageEventList.this.mList = null;
                            }
                            return true;
                        } catch (RuntimeException e) {
                            ParcelableUsageEventList.this.mList = null;
                            throw e;
                        }
                    }
                });
            }
        }
    }

    public List<UsageEvents.Event> getList() {
        return this.mList;
    }

    private UsageEvents.Event readEventFromParcel(Parcel parcel) {
        UsageEvents.Event event = new UsageEvents.Event();
        event.mPackage = parcel.readString();
        event.mClass = parcel.readString();
        event.mInstanceId = parcel.readInt();
        event.mTaskRootPackage = parcel.readString();
        event.mTaskRootClass = parcel.readString();
        event.mEventType = parcel.readInt();
        event.mTimeStamp = parcel.readLong();
        event.mConfiguration = null;
        event.mShortcutId = null;
        event.mAction = null;
        event.mContentType = null;
        event.mContentAnnotations = null;
        event.mNotificationChannelId = null;
        event.mLocusId = null;
        event.mExtras = null;
        int i = event.mEventType;
        if (i == 5) {
            event.mConfiguration = Configuration.CREATOR.createFromParcel(parcel);
        } else if (i == 30) {
            event.mLocusId = parcel.readString();
        } else if (i != 7) {
            if (i == 8) {
                event.mShortcutId = parcel.readString();
            } else if (i == 9) {
                event.mAction = parcel.readString();
                event.mContentType = parcel.readString();
                event.mContentAnnotations = parcel.readStringArray();
            } else if (i == 11) {
                event.mBucketAndReason = parcel.readInt();
            } else if (i == 12) {
                event.mNotificationChannelId = parcel.readString();
            }
        } else if (parcel.readInt() != 0) {
            event.mExtras = parcel.readPersistableBundle(getClass().getClassLoader());
        }
        event.mFlags = parcel.readInt();
        return event;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void writeEventToParcel(UsageEvents.Event event, Parcel parcel, int i) {
        parcel.writeString(event.mPackage);
        parcel.writeString(event.mClass);
        parcel.writeInt(event.mInstanceId);
        parcel.writeString(event.mTaskRootPackage);
        parcel.writeString(event.mTaskRootClass);
        parcel.writeInt(event.mEventType);
        parcel.writeLong(event.mTimeStamp);
        int i2 = event.mEventType;
        if (i2 == 5) {
            event.mConfiguration.writeToParcel(parcel, i);
        } else if (i2 == 30) {
            parcel.writeString(event.mLocusId);
        } else if (i2 != 7) {
            if (i2 == 8) {
                parcel.writeString(event.mShortcutId);
            } else if (i2 == 9) {
                parcel.writeString(event.mAction);
                parcel.writeString(event.mContentType);
                parcel.writeStringArray(event.mContentAnnotations);
            } else if (i2 == 11) {
                parcel.writeInt(event.mBucketAndReason);
            } else if (i2 == 12) {
                parcel.writeString(event.mNotificationChannelId);
            }
        } else if (event.mExtras != null) {
            parcel.writeInt(1);
            parcel.writePersistableBundle(event.mExtras);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(event.mFlags);
    }
}
