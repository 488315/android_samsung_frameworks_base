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

    SavedDatasetsInfoCallbackImpl(IResultReceiver receiver, String type) {
        this.mReceiver = (IResultReceiver) Objects.requireNonNull(receiver);
        this.mType = (String) Objects.requireNonNull(type);
    }

    @Override // android.service.autofill.SavedDatasetsInfoCallback
    public void onSuccess(Set<SavedDatasetsInfo> results) {
        Objects.requireNonNull(results);
        if (results.isEmpty()) {
            send(1, null);
            return;
        }
        int count = -1;
        for (SavedDatasetsInfo info : results) {
            if (this.mType.equals(info.getType())) {
                count = info.getCount();
            }
        }
        if (count < 0) {
            send(1, null);
            return;
        }
        Bundle bundle = new Bundle(1);
        bundle.putInt("result", count);
        send(0, bundle);
    }

    @Override // android.service.autofill.SavedDatasetsInfoCallback
    public void onError(int error) {
        Preconditions.checkArgumentInRange(error, 0, 2, "error");
        Bundle bundle = new Bundle(1);
        bundle.putInt("error", error);
        send(1, bundle);
    }

    private void send(int resultCode, Bundle bundle) {
        try {
            this.mReceiver.send(resultCode, bundle);
        } catch (DeadObjectException e) {
            Log.w(TAG, "Failed to send onSavedPasswordCountRequest result: " + e);
        } catch (RemoteException e2) {
            throw e2.rethrowAsRuntimeException();
        }
    }
}
