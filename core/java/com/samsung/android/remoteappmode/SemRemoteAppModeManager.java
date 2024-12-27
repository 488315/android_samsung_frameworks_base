package com.samsung.android.remoteappmode;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.service.notification.StatusBarNotification;
import android.util.ArrayMap;
import android.util.Log;
import android.view.Surface;

import java.util.Map;

/* loaded from: classes6.dex */
public final class SemRemoteAppModeManager {
    private static final String TAG = SemRemoteAppModeManager.class.getSimpleName();
    private static final Object sLock = new Object();
    private IRemoteAppMode mService;
    private Map<TaskChangeListener, TaskChangeListenerDelegate> mTaskChangeListeners = null;
    private Map<SecureAppChangedListener, SecureAppChangedListenerDelegate>
            mSecureAppChangedListeners = null;
    private Map<RotationChangedListener, RotationChangedListenerDelegate>
            mRotationChangedListeners = null;
    private Map<StartActivityInterceptedListener, StartActivityInterceptedListenerDelegate>
            mStartActivityInterceptedListeners = null;
    private Map<RemoteAppModeListener, RemoteAppModeListenerDelegate> mRemoteAppModeListeners =
            null;

    public interface RemoteAppModeListener {
        void onRemoteAppModeStateChanged(boolean z);
    }

    public interface RotationChangedListener {
        void onRotationChanged(int i, int i2);
    }

    public interface SecureAppChangedListener {
        void onSecuredAppLaunched(int i, String str);

        void onSecuredAppRemoved(int i, String str);
    }

    public interface StartActivityInterceptedListener {
        void onStartActivityIntercepted(
                Intent intent,
                Bundle bundle,
                ActivityInfo activityInfo,
                int i,
                boolean z,
                int i2,
                int i3,
                int i4);
    }

    public interface TaskChangeListener {
        void onRecentTaskListUpdated();

        void onTaskDisplayChanged(int i, int i2);

        void onTaskPlayed(int i, int i2);

        void onTaskRemoved(int i);

        void onTaskTriedToGoToBackground(int i, int i2);
    }

    public interface VirtualDisplayAliveChecker {
        void onVirtualDisplayCreated(int i);

        void onVirtualDisplayReleased(int i);
    }

    public int getProtocolVersion() {
        try {
            return this.mService.getProtocolVersion();
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return 1;
        }
    }

    public int createVirtualDisplay(
            String name,
            int width,
            int height,
            int densityDpi,
            Surface surface,
            VirtualDisplayAliveChecker checker) {
        try {
            return this.mService.createVirtualDisplay(
                    name,
                    width,
                    height,
                    densityDpi,
                    surface,
                    new VirtualDisplayAliveCheckerDelegate(checker));
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return -1;
        }
    }

    public void releaseVirtualDisplay(int displayId) {
        try {
            this.mService.releaseVirtualDisplay(displayId);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void resizeVirtualDisplay(
            int displayId, int width, int height, int densityDpi, Surface surface) {
        try {
            this.mService.resizeVirtualDisplay(displayId, width, height, densityDpi, surface);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void moveDisplayToTop(int displayId) {
        try {
            this.mService.moveDisplayToTop(displayId);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void launchApplication(
            int displayId, String packageName, Intent intent, Bundle activityOptionsBundle) {
        try {
            this.mService.launchApplication(displayId, packageName, intent, activityOptionsBundle);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void transferTaskWithoutIntercept(
            int taskId, int targetDisplayId, Bundle activityOptionsBundle) {
        try {
            this.mService.transferTaskWithoutIntercept(
                    taskId, targetDisplayId, activityOptionsBundle);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void transferTaskUsingIntent(
            Intent intent, int taskId, int targetDisplayId, Bundle activityOptionsBundle) {
        try {
            this.mService.transferTaskUsingIntent(
                    intent, taskId, targetDisplayId, activityOptionsBundle);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void setLTWProtocolVersion(int version) {
        try {
            this.mService.setLTWProtocolVersion(version);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void startRFCommService() {
        try {
            this.mService.startRFCommService();
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void stopRFCommService() {
        try {
            this.mService.stopRFCommService();
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    private static class VirtualDisplayAliveCheckerDelegate
            extends IVirtualDisplayAliveChecker.Stub {
        private VirtualDisplayAliveChecker mListener;

        VirtualDisplayAliveCheckerDelegate(VirtualDisplayAliveChecker listener) {
            this.mListener = listener;
        }

        @Override // com.samsung.android.remoteappmode.IVirtualDisplayAliveChecker
        public void onVirtualDisplayCreated(int displayId) throws RemoteException {
            VirtualDisplayAliveChecker listener;
            synchronized (SemRemoteAppModeManager.sLock) {
                listener = this.mListener;
            }
            if (listener != null) {
                Log.d(
                        SemRemoteAppModeManager.TAG,
                        "onVirtualDisplayCreated() displayId=" + displayId);
                listener.onVirtualDisplayCreated(displayId);
            }
        }

        @Override // com.samsung.android.remoteappmode.IVirtualDisplayAliveChecker
        public void onVirtualDisplayReleased(int displayId) throws RemoteException {
            VirtualDisplayAliveChecker listener;
            synchronized (SemRemoteAppModeManager.sLock) {
                listener = this.mListener;
            }
            if (listener != null) {
                Log.d(
                        SemRemoteAppModeManager.TAG,
                        "onVirtualDisplayReleased() displayId=" + displayId);
                listener.onVirtualDisplayReleased(displayId);
            }
        }

        public String toString() {
            String valueOf;
            synchronized (SemRemoteAppModeManager.sLock) {
                valueOf = String.valueOf(this.mListener);
            }
            return valueOf;
        }

        void nullOutListenerLocked() {
            this.mListener = null;
        }
    }

    private static class TaskChangeListenerDelegate extends ITaskChangeListener.Stub {
        private TaskChangeListener mListener;

        TaskChangeListenerDelegate(TaskChangeListener listener) {
            this.mListener = listener;
        }

        @Override // com.samsung.android.remoteappmode.ITaskChangeListener
        public void onTaskRemoved(int taskId) throws RemoteException {
            TaskChangeListener listener;
            synchronized (SemRemoteAppModeManager.sLock) {
                listener = this.mListener;
            }
            if (listener != null) {
                Log.d(
                        SemRemoteAppModeManager.TAG,
                        "onTaskRemoved() taskId=" + taskId + ", listener=" + listener);
                listener.onTaskRemoved(taskId);
            }
        }

        @Override // com.samsung.android.remoteappmode.ITaskChangeListener
        public void onTaskPlayed(int taskId, int displayId) throws RemoteException {
            TaskChangeListener listener;
            synchronized (SemRemoteAppModeManager.sLock) {
                listener = this.mListener;
            }
            if (listener != null) {
                Log.d(
                        SemRemoteAppModeManager.TAG,
                        "onTaskPlayed() taskId="
                                + taskId
                                + ", listener="
                                + listener
                                + ", displayId="
                                + displayId);
                listener.onTaskPlayed(taskId, displayId);
            }
        }

        @Override // com.samsung.android.remoteappmode.ITaskChangeListener
        public void onTaskTriedToGoToBackground(int taskId, int displayId) throws RemoteException {
            TaskChangeListener listener;
            synchronized (SemRemoteAppModeManager.sLock) {
                listener = this.mListener;
            }
            if (listener != null) {
                Log.d(
                        SemRemoteAppModeManager.TAG,
                        "onTaskTriedToGoToBackground() taskId="
                                + taskId
                                + ", listener="
                                + listener
                                + ", displayId="
                                + displayId);
                listener.onTaskTriedToGoToBackground(taskId, displayId);
            }
        }

        @Override // com.samsung.android.remoteappmode.ITaskChangeListener
        public void onTaskDisplayChanged(int taskId, int newDisplayId) throws RemoteException {
            TaskChangeListener listener;
            synchronized (SemRemoteAppModeManager.sLock) {
                listener = this.mListener;
            }
            if (listener != null) {
                Log.d(
                        SemRemoteAppModeManager.TAG,
                        "onTaskDisplayChanged() taskId="
                                + taskId
                                + ", listener="
                                + listener
                                + ", displayId="
                                + newDisplayId);
                listener.onTaskDisplayChanged(taskId, newDisplayId);
            }
        }

        @Override // com.samsung.android.remoteappmode.ITaskChangeListener
        public void onRecentTaskListUpdated() throws RemoteException {
            TaskChangeListener listener;
            synchronized (SemRemoteAppModeManager.sLock) {
                listener = this.mListener;
            }
            if (listener != null) {
                Log.d(SemRemoteAppModeManager.TAG, "onTaskStackUpdated()");
                listener.onRecentTaskListUpdated();
            }
        }

        public String toString() {
            String valueOf;
            synchronized (SemRemoteAppModeManager.sLock) {
                valueOf = String.valueOf(this.mListener);
            }
            return valueOf;
        }

        void nullOutListenerLocked() {
            this.mListener = null;
        }
    }

    public void registerTaskChangeListener(TaskChangeListener listener) {
        synchronized (sLock) {
            if (listener == null) {
                Log.w(TAG, "registerTaskChangeListener: Listener is null");
                return;
            }
            if (this.mTaskChangeListeners == null) {
                this.mTaskChangeListeners = new ArrayMap();
            }
            if (this.mTaskChangeListeners.containsKey(listener)) {
                Log.w(TAG, "registerTaskChangeListener: " + listener + " already registered");
                return;
            }
            TaskChangeListenerDelegate delegate = new TaskChangeListenerDelegate(listener);
            try {
                this.mService.registerTaskChangeListener(delegate, listener.toString());
                this.mTaskChangeListeners.put(listener, delegate);
                Log.i(TAG, "registerTaskChangeListener: " + listener);
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
        }
    }

    public void unregisterTaskChangeListener(TaskChangeListener listener) {
        synchronized (sLock) {
            if (listener == null) {
                Log.w(TAG, "unregisterTaskChangeListener: Listener is null");
                return;
            }
            if (this.mTaskChangeListeners == null) {
                return;
            }
            TaskChangeListenerDelegate delegate = this.mTaskChangeListeners.remove(listener);
            if (delegate == null) {
                Log.w(TAG, "unregisterTaskChangeListener: " + listener + " already unregistered");
                return;
            }
            if (this.mTaskChangeListeners.isEmpty()) {
                this.mTaskChangeListeners = null;
            }
            try {
                this.mService.unregisterTaskChangeListener(delegate);
                Log.i(TAG, "unregisterTaskChangeListener: " + listener);
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
            delegate.nullOutListenerLocked();
        }
    }

    public boolean isAllowed() {
        try {
            return this.mService.isAllowed();
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return false;
        }
    }

    public void getLastAnr(String packageName, ParcelFileDescriptor outputPfd) {
        try {
            this.mService.getLastAnr(packageName, outputPfd);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void enableSendingUserPresentIntent(String packageName) {
        try {
            this.mService.enableSendingUserPresentIntent(packageName);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void disableSendingUserPresentIntent() {
        try {
            this.mService.disableSendingUserPresentIntent();
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public boolean isSendingUserPresentEnabled() {
        try {
            return this.mService.isSendingUserPresentEnabled();
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return false;
        }
    }

    public void setSendingUserPresentExpiredTime(long expiredTimeMillis) {
        try {
            this.mService.setSendingUserPresentExpiredTime(expiredTimeMillis);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public long getSendingUserPresentExpiredTime() {
        try {
            return this.mService.getSendingUserPresentExpiredTime();
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return 0L;
        }
    }

    public SemRemoteAppModeManager(Context context, IRemoteAppMode service) {
        this.mService = service;
    }

    private static class RotationChangedListenerDelegate extends IRotationChangeListener.Stub {
        private RotationChangedListener mListener;

        RotationChangedListenerDelegate(RotationChangedListener listener) {
            this.mListener = listener;
        }

        @Override // com.samsung.android.remoteappmode.IRotationChangeListener
        public void onRotationChanged(int displayId, int rotation) throws RemoteException {
            RotationChangedListener listener;
            synchronized (SemRemoteAppModeManager.sLock) {
                listener = this.mListener;
            }
            if (listener != null) {
                Log.d(
                        SemRemoteAppModeManager.TAG,
                        "onRotationChanged() displayId="
                                + displayId
                                + ", rotation="
                                + rotation
                                + ", listener="
                                + listener);
                listener.onRotationChanged(displayId, rotation);
            }
        }

        public String toString() {
            String valueOf;
            synchronized (SemRemoteAppModeManager.sLock) {
                valueOf = String.valueOf(this.mListener);
            }
            return valueOf;
        }

        void nullOutListenerLocked() {
            this.mListener = null;
        }
    }

    public void registerRotationChangeListener(RotationChangedListener listener, int displayId) {
        synchronized (sLock) {
            if (listener == null) {
                Log.w(TAG, "registerRotationChangeListener: Listener is null");
                return;
            }
            if (this.mRotationChangedListeners == null) {
                this.mRotationChangedListeners = new ArrayMap();
            }
            if (this.mRotationChangedListeners.containsKey(listener)) {
                Log.w(TAG, "registerListener: " + listener + " already registered");
                return;
            }
            RotationChangedListenerDelegate delegate =
                    new RotationChangedListenerDelegate(listener);
            try {
                this.mService.registerRotationChangeListener(
                        delegate, listener.toString(), displayId);
                this.mRotationChangedListeners.put(listener, delegate);
                Log.i(TAG, "registerRotationChangeListener: " + listener);
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
        }
    }

    public void unregisterRotationChangeListener(RotationChangedListener listener) {
        synchronized (sLock) {
            if (listener == null) {
                Log.w(TAG, "unregisterRotationChangeListener: Listener is null");
                return;
            }
            if (this.mRotationChangedListeners == null) {
                return;
            }
            RotationChangedListenerDelegate delegate =
                    this.mRotationChangedListeners.remove(listener);
            if (delegate == null) {
                Log.w(
                        TAG,
                        "unregisterRotationChangeListener: " + listener + " already unregistered");
                return;
            }
            if (this.mRotationChangedListeners.isEmpty()) {
                this.mRotationChangedListeners = null;
            }
            try {
                this.mService.unregisterRotationChangeListener(delegate);
                Log.i(TAG, "unregisterRotationChangeListener: " + listener);
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
            delegate.nullOutListenerLocked();
        }
    }

    private static class SecureAppChangedListenerDelegate extends ISecureAppChangedListener.Stub {
        private SecureAppChangedListener mListener;

        SecureAppChangedListenerDelegate(SecureAppChangedListener listener) {
            this.mListener = listener;
        }

        @Override // com.samsung.android.remoteappmode.ISecureAppChangedListener
        public void onSecuredAppLaunched(int taskId, String packageName) throws RemoteException {
            SecureAppChangedListener listener;
            synchronized (SemRemoteAppModeManager.sLock) {
                listener = this.mListener;
            }
            if (listener != null) {
                Log.d(
                        SemRemoteAppModeManager.TAG,
                        "onSecuredAppLaunched() taskId="
                                + taskId
                                + ", packageName="
                                + packageName
                                + ", listener="
                                + listener);
                listener.onSecuredAppLaunched(taskId, packageName);
            }
        }

        @Override // com.samsung.android.remoteappmode.ISecureAppChangedListener
        public void onSecuredAppRemoved(int taskId, String packageName) throws RemoteException {
            SecureAppChangedListener listener;
            synchronized (SemRemoteAppModeManager.sLock) {
                listener = this.mListener;
            }
            if (listener != null) {
                Log.d(
                        SemRemoteAppModeManager.TAG,
                        "onSecuredAppRemoved() taskId="
                                + taskId
                                + ", packageName="
                                + packageName
                                + ", listener="
                                + listener);
                listener.onSecuredAppRemoved(taskId, packageName);
            }
        }

        public String toString() {
            String valueOf;
            synchronized (SemRemoteAppModeManager.sLock) {
                valueOf = String.valueOf(this.mListener);
            }
            return valueOf;
        }

        void nullOutListenerLocked() {
            this.mListener = null;
        }
    }

    public void registerSecureAppChangedListener(SecureAppChangedListener listener) {
        synchronized (sLock) {
            if (listener == null) {
                Log.w(TAG, "registerSecureAppChangedListener: Listener is null");
                return;
            }
            if (this.mSecureAppChangedListeners == null) {
                this.mSecureAppChangedListeners = new ArrayMap();
            }
            if (this.mSecureAppChangedListeners.containsKey(listener)) {
                Log.w(TAG, "registerSecureAppChangedListener: " + listener + " already registered");
                return;
            }
            SecureAppChangedListenerDelegate delegate =
                    new SecureAppChangedListenerDelegate(listener);
            try {
                this.mService.registerSecureAppChangedListener(delegate, listener.toString());
                this.mSecureAppChangedListeners.put(listener, delegate);
                Log.i(TAG, "registerSecureAppChangedListener: " + listener);
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
        }
    }

    public void unregisterSecureAppChangedListener(SecureAppChangedListener listener) {
        synchronized (sLock) {
            if (listener == null) {
                Log.w(TAG, "unregisterSecureAppChangedListener: Listener is null");
                return;
            }
            if (this.mSecureAppChangedListeners == null) {
                return;
            }
            SecureAppChangedListenerDelegate delegate =
                    this.mSecureAppChangedListeners.remove(listener);
            if (delegate == null) {
                Log.w(
                        TAG,
                        "unregisterSecureAppChangedListener: "
                                + listener
                                + " already unregistered");
                return;
            }
            if (this.mSecureAppChangedListeners.isEmpty()) {
                this.mSecureAppChangedListeners = null;
            }
            try {
                this.mService.unregisterSecureAppChangedListener(delegate);
                Log.i(TAG, "unregisterSecureAppChangedListener: " + listener);
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
            delegate.nullOutListenerLocked();
        }
    }

    public void clearAll() {
        synchronized (sLock) {
            try {
                this.mService.clearAll();
                if (this.mTaskChangeListeners != null) {
                    this.mTaskChangeListeners.clear();
                }
                if (this.mSecureAppChangedListeners != null) {
                    this.mSecureAppChangedListeners.clear();
                }
                if (this.mStartActivityInterceptedListeners != null) {
                    this.mStartActivityInterceptedListeners.clear();
                }
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
        }
    }

    private static class StartActivityInterceptedListenerDelegate
            extends IStartActivityInterceptListener.Stub {
        private StartActivityInterceptedListener mListener;

        StartActivityInterceptedListenerDelegate(StartActivityInterceptedListener listener) {
            this.mListener = listener;
        }

        @Override // com.samsung.android.remoteappmode.IStartActivityInterceptListener
        public void onStartActivityIntercepted(
                Intent intent,
                Bundle activityOptionsBundle,
                ActivityInfo activityInfo,
                int interceptedDisplayId,
                boolean isVisibleTask,
                int runningTaskId,
                int runningTaskDisplayId,
                int interceptReason)
                throws RemoteException {
            StartActivityInterceptedListener listener;
            synchronized (SemRemoteAppModeManager.sLock) {
                try {
                    listener = this.mListener;
                } catch (Throwable th) {
                    th = th;
                    while (true) {
                        try {
                            throw th;
                        } catch (Throwable th2) {
                            th = th2;
                        }
                    }
                }
            }
            if (listener != null) {
                Log.d(
                        SemRemoteAppModeManager.TAG,
                        "onStartActivityIntercepted() interceptedDisplayId="
                                + interceptedDisplayId
                                + ", intent="
                                + intent.toString()
                                + ", listener="
                                + listener);
                listener.onStartActivityIntercepted(
                        intent,
                        activityOptionsBundle,
                        activityInfo,
                        interceptedDisplayId,
                        isVisibleTask,
                        runningTaskId,
                        runningTaskDisplayId,
                        interceptReason);
            }
        }

        public String toString() {
            String valueOf;
            synchronized (SemRemoteAppModeManager.sLock) {
                valueOf = String.valueOf(this.mListener);
            }
            return valueOf;
        }

        void nullOutListenerLocked() {
            this.mListener = null;
        }
    }

    public void registerStartActivityInterceptedListener(
            StartActivityInterceptedListener listener) {
        synchronized (sLock) {
            if (listener == null) {
                Log.w(TAG, "registerListener: Listener is null");
                return;
            }
            if (this.mStartActivityInterceptedListeners == null) {
                this.mStartActivityInterceptedListeners = new ArrayMap();
            }
            if (this.mStartActivityInterceptedListeners.containsKey(listener)) {
                Log.w(TAG, "registerListener: " + listener + " already registered");
                return;
            }
            StartActivityInterceptedListenerDelegate delegate =
                    new StartActivityInterceptedListenerDelegate(listener);
            try {
                this.mService.registerStartActivityInterceptListener(delegate, listener.toString());
                this.mStartActivityInterceptedListeners.put(listener, delegate);
                Log.i(TAG, "registerListener: " + listener);
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
        }
    }

    public void unregisterStartActivityInterceptedListener(
            StartActivityInterceptedListener listener) {
        synchronized (sLock) {
            if (listener == null) {
                Log.w(TAG, "unregisterListener: Listener is null");
                return;
            }
            if (this.mStartActivityInterceptedListeners == null) {
                return;
            }
            StartActivityInterceptedListenerDelegate delegate =
                    this.mStartActivityInterceptedListeners.remove(listener);
            if (delegate == null) {
                Log.w(TAG, "unregisterListener: " + listener + " already unregistered");
                return;
            }
            if (this.mStartActivityInterceptedListeners.isEmpty()) {
                this.mStartActivityInterceptedListeners = null;
            }
            try {
                this.mService.unregisterStartActivityInterceptListener(delegate);
                Log.i(TAG, "unregisterListener: " + listener);
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
            delegate.nullOutListenerLocked();
        }
    }

    public void sendPendingIntent(PendingIntent pendingIntent) {
        try {
            this.mService.sendPendingIntent(pendingIntent);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public boolean sendNotificationContent(StatusBarNotification sbn) {
        try {
            return this.mService.sendNotificationContent(sbn);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return false;
        }
    }

    public boolean sendNotificationAction(
            StatusBarNotification sbn, int actionIndex, Intent intent) {
        try {
            return this.mService.sendNotificationAction(sbn, actionIndex, intent);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return false;
        }
    }

    public void forceStopPackage(String packageName) {
        try {
            this.mService.forceStopPackage(packageName);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    private static class RemoteAppModeListenerDelegate extends IRemoteAppModeListener.Stub {
        private RemoteAppModeListener mListener;

        RemoteAppModeListenerDelegate(RemoteAppModeListener listener) {
            this.mListener = listener;
        }

        @Override // com.samsung.android.remoteappmode.IRemoteAppModeListener
        public void onRemoteAppModeStateChanged(boolean isEnabled) throws RemoteException {
            RemoteAppModeListener listener;
            synchronized (SemRemoteAppModeManager.sLock) {
                listener = this.mListener;
            }
            if (listener != null) {
                Log.d(
                        SemRemoteAppModeManager.TAG,
                        "onRemoteAppModeStateChanged() isEnabled="
                                + isEnabled
                                + ", listener="
                                + listener);
                listener.onRemoteAppModeStateChanged(isEnabled);
            }
        }

        public String toString() {
            String valueOf;
            synchronized (SemRemoteAppModeManager.sLock) {
                valueOf = String.valueOf(this.mListener);
            }
            return valueOf;
        }

        void nullOutListenerLocked() {
            this.mListener = null;
        }
    }

    public void registerRemoteAppModeListener(RemoteAppModeListener listener) {
        synchronized (sLock) {
            if (listener == null) {
                Log.w(TAG, "registerRemoteAppModeListener: Listener is null");
                return;
            }
            if (this.mRemoteAppModeListeners == null) {
                this.mRemoteAppModeListeners = new ArrayMap();
            }
            if (this.mRemoteAppModeListeners.containsKey(listener)) {
                Log.w(TAG, "registerRemoteAppModeListener: " + listener + " already registered");
                return;
            }
            RemoteAppModeListenerDelegate delegate = new RemoteAppModeListenerDelegate(listener);
            try {
                this.mService.registerRemoteAppModeListener(delegate, listener.toString());
                this.mRemoteAppModeListeners.put(listener, delegate);
                Log.i(TAG, "registerRemoteAppModeListener: " + listener);
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
        }
    }

    public void unregisterRemoteAppModeListener(RemoteAppModeListener listener) {
        synchronized (sLock) {
            if (listener == null) {
                Log.w(TAG, "unregisterRemoteAppModeListener: Listener is null");
                return;
            }
            if (this.mRemoteAppModeListeners == null) {
                return;
            }
            RemoteAppModeListenerDelegate delegate = this.mRemoteAppModeListeners.remove(listener);
            if (delegate == null) {
                Log.w(
                        TAG,
                        "unregisterRemoteAppModeListener: " + listener + " already unregistered");
                return;
            }
            if (this.mRemoteAppModeListeners.isEmpty()) {
                this.mRemoteAppModeListeners = null;
            }
            try {
                this.mService.unregisterRemoteAppModeListener(delegate);
                Log.i(TAG, "unregisterRemoteAppModeListener: " + listener);
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
            delegate.nullOutListenerLocked();
        }
    }
}
