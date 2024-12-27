package com.android.systemui.statusbar.notification.row;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.Trace;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.util.settings.SecureSettings;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public final class NotificationSettingsController implements Dumpable {
    public final Handler mBackgroundHandler;
    public final AnonymousClass1 mContentObserver;
    public final UserTracker.Callback mCurrentUserTrackerCallback;
    public final HashMap mListeners = new HashMap();
    public final Handler mMainHandler;
    public final SecureSettings mSecureSettings;
    public final UserTracker mUserTracker;

    public interface Listener {
    }

    public NotificationSettingsController(UserTracker userTracker, Handler handler, Handler handler2, SecureSettings secureSettings, DumpManager dumpManager) {
        this.mUserTracker = userTracker;
        this.mMainHandler = handler;
        this.mBackgroundHandler = handler2;
        this.mSecureSettings = secureSettings;
        this.mContentObserver = new ContentObserver(handler2) { // from class: com.android.systemui.statusbar.notification.row.NotificationSettingsController.1
            @Override // android.database.ContentObserver
            public final void onChange(boolean z, Uri uri) {
                String lastPathSegment;
                Trace.traceBegin(4096L, "NotificationSettingsController.ContentObserver.onChange");
                super.onChange(z, uri);
                synchronized (NotificationSettingsController.this.mListeners) {
                    try {
                        if (NotificationSettingsController.this.mListeners.containsKey(uri)) {
                            int userId = ((UserTrackerImpl) NotificationSettingsController.this.mUserTracker).getUserId();
                            NotificationSettingsController notificationSettingsController = NotificationSettingsController.this;
                            if (uri == null) {
                                lastPathSegment = null;
                            } else {
                                notificationSettingsController.getClass();
                                lastPathSegment = uri.getLastPathSegment();
                            }
                            String stringForUser = notificationSettingsController.mSecureSettings.getStringForUser(lastPathSegment, userId);
                            Iterator it = ((ArrayList) NotificationSettingsController.this.mListeners.get(uri)).iterator();
                            while (it.hasNext()) {
                                NotificationSettingsController.this.mMainHandler.post(new NotificationSettingsController$$ExternalSyntheticLambda3((Listener) it.next(), uri, userId, stringForUser));
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                Trace.traceEnd(4096L);
            }
        };
        UserTracker.Callback callback = new UserTracker.Callback() { // from class: com.android.systemui.statusbar.notification.row.NotificationSettingsController.2
            @Override // com.android.systemui.settings.UserTracker.Callback
            public final void onUserChanged(int i, Context context) {
                Trace.traceBegin(4096L, "NotificationSettingsController.UserTracker.Callback.onUserChanged");
                synchronized (NotificationSettingsController.this.mListeners) {
                    try {
                        if (NotificationSettingsController.this.mListeners.size() > 0) {
                            NotificationSettingsController notificationSettingsController = NotificationSettingsController.this;
                            notificationSettingsController.mSecureSettings.unregisterContentObserverSync(notificationSettingsController.mContentObserver);
                            for (Uri uri : NotificationSettingsController.this.mListeners.keySet()) {
                                NotificationSettingsController notificationSettingsController2 = NotificationSettingsController.this;
                                notificationSettingsController2.mSecureSettings.registerContentObserverForUserSync(uri, false, (ContentObserver) notificationSettingsController2.mContentObserver, i);
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                Trace.traceEnd(4096L);
            }
        };
        this.mCurrentUserTrackerCallback = callback;
        ((UserTrackerImpl) userTracker).addCallback(callback, new HandlerExecutor(handler2));
        dumpManager.registerNormalDumpable("NotificationSettingsController", this);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        Trace.traceBegin(4096L, "NotificationSettingsController.dump");
        synchronized (this.mListeners) {
            try {
                printWriter.println("Settings Uri Listener List:");
                for (Uri uri : this.mListeners.keySet()) {
                    printWriter.println("   Uri=" + uri);
                    Iterator it = ((ArrayList) this.mListeners.get(uri)).iterator();
                    while (it.hasNext()) {
                        printWriter.println("      Listener=" + ((Listener) it.next()).getClass().getName());
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        Trace.traceEnd(4096L);
    }
}
