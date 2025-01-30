package com.android.server.power;

import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.ArraySet;
import android.util.Pair;
import com.android.internal.statusbar.IStatusBarService;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/* loaded from: classes3.dex */
public class AmbientDisplaySuppressionController {
    public final AmbientDisplaySuppressionChangedCallback mCallback;
    public IStatusBarService mStatusBarService;
    public final Set mSuppressionTokens = Collections.synchronizedSet(new ArraySet());

    public interface AmbientDisplaySuppressionChangedCallback {
        void onSuppressionChanged(boolean z);
    }

    public AmbientDisplaySuppressionController(AmbientDisplaySuppressionChangedCallback ambientDisplaySuppressionChangedCallback) {
        Objects.requireNonNull(ambientDisplaySuppressionChangedCallback);
        this.mCallback = ambientDisplaySuppressionChangedCallback;
    }

    public void suppress(String str, int i, boolean z) {
        Objects.requireNonNull(str);
        Pair create = Pair.create(str, Integer.valueOf(i));
        boolean isSuppressed = isSuppressed();
        if (z) {
            this.mSuppressionTokens.add(create);
        } else {
            this.mSuppressionTokens.remove(create);
        }
        boolean isSuppressed2 = isSuppressed();
        if (isSuppressed2 != isSuppressed) {
            this.mCallback.onSuppressionChanged(isSuppressed2);
        }
        try {
            synchronized (this.mSuppressionTokens) {
                getStatusBar().suppressAmbientDisplay(isSuppressed2);
            }
        } catch (RemoteException e) {
            android.util.Slog.e("AmbientDisplaySuppressionController", "Failed to suppress ambient display", e);
        }
    }

    public List getSuppressionTokens(int i) {
        ArrayList arrayList = new ArrayList();
        synchronized (this.mSuppressionTokens) {
            for (Pair pair : this.mSuppressionTokens) {
                if (((Integer) pair.second).intValue() == i) {
                    arrayList.add((String) pair.first);
                }
            }
        }
        return arrayList;
    }

    public boolean isSuppressed(String str, int i) {
        Set set = this.mSuppressionTokens;
        Objects.requireNonNull(str);
        return set.contains(Pair.create(str, Integer.valueOf(i)));
    }

    public boolean isSuppressed() {
        return !this.mSuppressionTokens.isEmpty();
    }

    public void dump(PrintWriter printWriter) {
        printWriter.println("AmbientDisplaySuppressionController:");
        printWriter.println(" ambientDisplaySuppressed=" + isSuppressed());
        printWriter.println(" mSuppressionTokens=" + this.mSuppressionTokens);
    }

    public final synchronized IStatusBarService getStatusBar() {
        if (this.mStatusBarService == null) {
            this.mStatusBarService = IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar"));
        }
        return this.mStatusBarService;
    }
}
