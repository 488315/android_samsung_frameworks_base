package com.android.settingslib.suggestions;

import android.app.LoaderManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.os.Process;
import android.os.UserHandle;
import android.service.settings.suggestions.Suggestion;
import android.support.v4.media.AbstractC0000x2c234b15;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.suggestions.SuggestionController;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@Deprecated
/* loaded from: classes.dex */
public class SuggestionControllerMixin implements SuggestionController.ServiceConnectionListener, LifecycleObserver, LoaderManager.LoaderCallbacks<List<Suggestion>> {
    public final Context mContext;
    public final SuggestionController mSuggestionController;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface SuggestionControllerHost {
    }

    public SuggestionControllerMixin(Context context, SuggestionControllerHost suggestionControllerHost, Lifecycle lifecycle, ComponentName componentName) {
        Context applicationContext = context.getApplicationContext();
        this.mContext = applicationContext;
        this.mSuggestionController = new SuggestionController(applicationContext, componentName, this);
        if (lifecycle != null) {
            lifecycle.addObserver(this);
        }
    }

    @Override // android.app.LoaderManager.LoaderCallbacks
    public final Loader<List<Suggestion>> onCreateLoader(int i, Bundle bundle) {
        if (i == 42) {
            return new SuggestionLoader(this.mContext, this.mSuggestionController);
        }
        throw new IllegalArgumentException(AbstractC0000x2c234b15.m0m("This loader id is not supported ", i));
    }

    @Override // android.app.LoaderManager.LoaderCallbacks
    public final void onLoadFinished(Loader<List<Suggestion>> loader, List<Suggestion> list) {
        throw null;
    }

    @Override // com.android.settingslib.suggestions.SuggestionController.ServiceConnectionListener
    public final void onServiceConnected() {
        throw null;
    }

    @Override // com.android.settingslib.suggestions.SuggestionController.ServiceConnectionListener
    public final void onServiceDisconnected() {
        throw null;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        SuggestionController suggestionController = this.mSuggestionController;
        suggestionController.getClass();
        UserHandle myUserHandle = Process.myUserHandle();
        suggestionController.mContext.bindServiceAsUser(suggestionController.mServiceIntent, suggestionController.mServiceConnection, 1, myUserHandle);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        SuggestionController suggestionController = this.mSuggestionController;
        if (suggestionController.mRemoteService != null) {
            suggestionController.mRemoteService = null;
            suggestionController.mContext.unbindService(suggestionController.mServiceConnection);
        }
    }

    @Override // android.app.LoaderManager.LoaderCallbacks
    public final void onLoaderReset(Loader<List<Suggestion>> loader) {
    }
}
