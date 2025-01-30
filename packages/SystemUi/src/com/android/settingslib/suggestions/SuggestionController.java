package com.android.settingslib.suggestions;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.service.settings.suggestions.ISuggestionService;
import android.util.Log;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SuggestionController {
    public final ServiceConnectionListener mConnectionListener;
    public final Context mContext;
    public ISuggestionService mRemoteService;
    public final ServiceConnectionC09321 mServiceConnection = new ServiceConnection() { // from class: com.android.settingslib.suggestions.SuggestionController.1
        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            SuggestionController.this.mRemoteService = ISuggestionService.Stub.asInterface(iBinder);
            ServiceConnectionListener serviceConnectionListener = SuggestionController.this.mConnectionListener;
            if (serviceConnectionListener != null) {
                serviceConnectionListener.onServiceConnected();
            }
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {
            SuggestionController suggestionController = SuggestionController.this;
            ServiceConnectionListener serviceConnectionListener = suggestionController.mConnectionListener;
            if (serviceConnectionListener != null) {
                suggestionController.mRemoteService = null;
                serviceConnectionListener.onServiceDisconnected();
            }
        }
    };
    public final Intent mServiceIntent;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface ServiceConnectionListener {
        void onServiceConnected();

        void onServiceDisconnected();
    }

    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.settingslib.suggestions.SuggestionController$1] */
    public SuggestionController(Context context, ComponentName componentName, ServiceConnectionListener serviceConnectionListener) {
        this.mContext = context.getApplicationContext();
        this.mConnectionListener = serviceConnectionListener;
        this.mServiceIntent = new Intent().setComponent(componentName);
    }

    public final List getSuggestions() {
        ISuggestionService iSuggestionService = this.mRemoteService;
        if (!(iSuggestionService != null)) {
            return null;
        }
        try {
            return iSuggestionService.getSuggestions();
        } catch (RemoteException | RuntimeException e) {
            Log.w("SuggestionController", "Error when calling getSuggestion()", e);
            return null;
        } catch (NullPointerException e2) {
            Log.w("SuggestionController", "mRemote service detached before able to query", e2);
            return null;
        }
    }
}
