package com.android.internal.os;

import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.IndentingPrintWriter;
import java.util.Iterator;

/* loaded from: classes5.dex */
public class BinderDeathDispatcher<T extends IInterface> {
    private static final String TAG = "BinderDeathDispatcher";
    private final Object mLock = new Object();
    private final ArrayMap<IBinder, BinderDeathDispatcher<T>.RecipientsInfo> mTargets = new ArrayMap<>();

    class RecipientsInfo implements IBinder.DeathRecipient {
        ArraySet<IBinder.DeathRecipient> mRecipients;
        final IBinder mTarget;

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
        }

        private RecipientsInfo(IBinder iBinder) {
            this.mRecipients = new ArraySet<>();
            this.mTarget = iBinder;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied(IBinder iBinder) {
            ArraySet<IBinder.DeathRecipient> arraySet;
            synchronized (BinderDeathDispatcher.this.mLock) {
                arraySet = this.mRecipients;
                this.mRecipients = null;
                BinderDeathDispatcher.this.mTargets.remove(this.mTarget);
            }
            if (arraySet == null) {
                return;
            }
            int size = arraySet.size();
            for (int i = 0; i < size; i++) {
                arraySet.valueAt(i).binderDied(iBinder);
            }
        }
    }

    public int linkToDeath(T t, IBinder.DeathRecipient deathRecipient) {
        int size;
        IBinder asBinder = t.asBinder();
        synchronized (this.mLock) {
            BinderDeathDispatcher<T>.RecipientsInfo recipientsInfo = this.mTargets.get(asBinder);
            if (recipientsInfo == null) {
                recipientsInfo = new RecipientsInfo(asBinder);
                try {
                    asBinder.linkToDeath(recipientsInfo, 0);
                    this.mTargets.put(asBinder, recipientsInfo);
                } catch (RemoteException unused) {
                    return -1;
                }
            }
            recipientsInfo.mRecipients.add(deathRecipient);
            size = recipientsInfo.mRecipients.size();
        }
        return size;
    }

    public void unlinkToDeath(T t, IBinder.DeathRecipient deathRecipient) {
        IBinder asBinder = t.asBinder();
        synchronized (this.mLock) {
            BinderDeathDispatcher<T>.RecipientsInfo recipientsInfo = this.mTargets.get(asBinder);
            if (recipientsInfo == null) {
                return;
            }
            if (recipientsInfo.mRecipients.remove(deathRecipient) && recipientsInfo.mRecipients.size() == 0) {
                recipientsInfo.mTarget.unlinkToDeath(recipientsInfo, 0);
                this.mTargets.remove(recipientsInfo.mTarget);
            }
        }
    }

    public void dump(IndentingPrintWriter indentingPrintWriter) {
        synchronized (this.mLock) {
            indentingPrintWriter.print("# of watched binders: ");
            indentingPrintWriter.println(this.mTargets.size());
            indentingPrintWriter.print("# of death recipients: ");
            Iterator<BinderDeathDispatcher<T>.RecipientsInfo> it = this.mTargets.values().iterator();
            int i = 0;
            while (it.hasNext()) {
                i += it.next().mRecipients.size();
            }
            indentingPrintWriter.println(i);
        }
    }

    public ArrayMap<IBinder, BinderDeathDispatcher<T>.RecipientsInfo> getTargetsForTest() {
        return this.mTargets;
    }
}
