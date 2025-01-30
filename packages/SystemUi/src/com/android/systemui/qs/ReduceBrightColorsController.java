package com.android.systemui.qs;

import android.content.Context;
import android.database.ContentObserver;
import android.hardware.display.ColorDisplayManager;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerExecutor;
import com.android.systemui.qs.tiles.ReduceBrightColorsTile;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.policy.CallbackController;
import com.android.systemui.util.settings.SecureSettings;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ReduceBrightColorsController implements CallbackController {
    public final C20611 mContentObserver;
    public UserTracker.Callback mCurrentUserTrackerCallback;
    public final ArrayList mListeners = new ArrayList();
    public final ColorDisplayManager mManager;
    public final SecureSettings mSecureSettings;
    public final UserTracker mUserTracker;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Listener {
    }

    /* JADX WARN: Type inference failed for: r4v1, types: [com.android.systemui.qs.ReduceBrightColorsController$1] */
    public ReduceBrightColorsController(UserTracker userTracker, Handler handler, ColorDisplayManager colorDisplayManager, SecureSettings secureSettings) {
        this.mManager = colorDisplayManager;
        this.mUserTracker = userTracker;
        this.mSecureSettings = secureSettings;
        this.mContentObserver = new ContentObserver(handler) { // from class: com.android.systemui.qs.ReduceBrightColorsController.1
            @Override // android.database.ContentObserver
            public final void onChange(boolean z, Uri uri) {
                super.onChange(z, uri);
                String lastPathSegment = uri == null ? null : uri.getLastPathSegment();
                synchronized (ReduceBrightColorsController.this.mListeners) {
                    if (lastPathSegment != null) {
                        if (ReduceBrightColorsController.this.mListeners.size() != 0 && lastPathSegment.equals("reduce_bright_colors_activated")) {
                            Iterator it = ReduceBrightColorsController.this.mListeners.iterator();
                            while (it.hasNext()) {
                                Listener listener = (Listener) it.next();
                                ReduceBrightColorsController.this.mManager.isReduceBrightColorsActivated();
                                ((ReduceBrightColorsTile) listener).refreshState(null);
                            }
                        }
                    }
                }
            }
        };
        UserTracker.Callback callback = new UserTracker.Callback() { // from class: com.android.systemui.qs.ReduceBrightColorsController.2
            @Override // com.android.systemui.settings.UserTracker.Callback
            public final void onUserChanged(int i, Context context) {
                synchronized (ReduceBrightColorsController.this.mListeners) {
                    if (ReduceBrightColorsController.this.mListeners.size() > 0) {
                        ReduceBrightColorsController reduceBrightColorsController = ReduceBrightColorsController.this;
                        reduceBrightColorsController.mSecureSettings.unregisterContentObserver(reduceBrightColorsController.mContentObserver);
                        ReduceBrightColorsController reduceBrightColorsController2 = ReduceBrightColorsController.this;
                        reduceBrightColorsController2.mSecureSettings.registerContentObserverForUser("reduce_bright_colors_activated", false, (ContentObserver) reduceBrightColorsController2.mContentObserver, i);
                    }
                }
            }
        };
        this.mCurrentUserTrackerCallback = callback;
        ((UserTrackerImpl) userTracker).addCallback(callback, new HandlerExecutor(handler));
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void addCallback(Object obj) {
        Listener listener = (Listener) obj;
        synchronized (this.mListeners) {
            if (!this.mListeners.contains(listener)) {
                this.mListeners.add(listener);
                if (this.mListeners.size() == 1) {
                    this.mSecureSettings.registerContentObserverForUser("reduce_bright_colors_activated", false, (ContentObserver) this.mContentObserver, ((UserTrackerImpl) this.mUserTracker).getUserId());
                }
            }
        }
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void removeCallback(Object obj) {
        Listener listener = (Listener) obj;
        synchronized (this.mListeners) {
            if (this.mListeners.remove(listener) && this.mListeners.size() == 0) {
                this.mSecureSettings.unregisterContentObserver(this.mContentObserver);
            }
        }
    }
}
