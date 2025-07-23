package android.service.autofill;

import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.RemoteException;
import android.util.Log;
import com.android.internal.os.IResultReceiver;
import com.android.internal.util.Preconditions;
import java.util.Objects;
import java.util.Set;

/* loaded from: classes3.dex */
final class SavedDatasetsInfoCallbackImpl implements SavedDatasetsInfoCallback {
    private static final String TAG = "AutofillService";
    private final IResultReceiver mReceiver;
    private final String mType;

    SavedDatasetsInfoCallbackImpl(IResultReceiver iResultReceiver, String str) {
        this.mReceiver = (IResultReceiver) Objects.requireNonNull(iResultReceiver);
        this.mType = (String) Objects.requireNonNull(str);
    }

    @Override // android.service.autofill.SavedDatasetsInfoCallback
    public void onSuccess(Set<SavedDatasetsInfo> set) {
        Objects.requireNonNull(set);
        if (set.isEmpty()) {
            send(1, null);
            return;
        }
        int i = -1;
        for (SavedDatasetsInfo savedDatasetsInfo : set) {
            if (this.mType.equals(savedDatasetsInfo.getType())) {
                i = savedDatasetsInfo.getCount();
            }
        }
        if (i < 0) {
            send(1, null);
            return;
        }
        Bundle bundle = new Bundle(1);
        bundle.putInt("result", i);
        send(0, bundle);
    }

    @Override // android.service.autofill.SavedDatasetsInfoCallback
    public void onError(int i) {
        Preconditions.checkArgumentInRange(i, 0, 2, "error");
        Bundle bundle = new Bundle(1);
        bundle.putInt("error", i);
        send(1, bundle);
    }

    private void send(int i, Bundle bundle) {
        try {
            this.mReceiver.send(i, bundle);
        } catch (DeadObjectException e) {
            Log.w(TAG, "Failed to send onSavedPasswordCountRequest result: " + e);
        } catch (RemoteException e2) {
            throw e2.rethrowAsRuntimeException();
        }
    }
}
