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
import com.samsung.android.remoteappmode.IRemoteAppModeListener;
import com.samsung.android.remoteappmode.IRotationChangeListener;
import com.samsung.android.remoteappmode.ISecureAppChangedListener;
import com.samsung.android.remoteappmode.IStartActivityInterceptListener;
import com.samsung.android.remoteappmode.ITaskChangeListener;
import com.samsung.android.remoteappmode.IVirtualDisplayAliveChecker;
import java.util.Map;

/* loaded from: classes6.dex */
public final class SemRemoteAppModeManager {
    private static final String TAG = "SemRemoteAppModeManager";
    private static final Object sLock = new Object();
    private IRemoteAppMode mService;
    private Map<TaskChangeListener, TaskChangeListenerDelegate> mTaskChangeListeners = null;
    private Map<SecureAppChangedListener, SecureAppChangedListenerDelegate> mSecureAppChangedListeners = null;
    private Map<RotationChangedListener, RotationChangedListenerDelegate> mRotationChangedListeners = null;
    private Map<StartActivityInterceptedListener, StartActivityInterceptedListenerDelegate> mStartActivityInterceptedListeners = null;
    private Map<RemoteAppModeListener, RemoteAppModeListenerDelegate> mRemoteAppModeListeners = null;

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
        void onStartActivityIntercepted(Intent intent, Bundle bundle, ActivityInfo activityInfo, int i, boolean z, int i2, int i3, int i4);
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

    public int createVirtualDisplay(String str, int i, int i2, int i3, Surface surface, VirtualDisplayAliveChecker virtualDisplayAliveChecker) {
        try {
            return this.mService.createVirtualDisplay(str, i, i2, i3, surface, new VirtualDisplayAliveCheckerDelegate(virtualDisplayAliveChecker));
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return -1;
        }
    }

    public void releaseVirtualDisplay(int i) {
        try {
            this.mService.releaseVirtualDisplay(i);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void resizeVirtualDisplay(int i, int i2, int i3, int i4, Surface surface) {
        try {
            this.mService.resizeVirtualDisplay(i, i2, i3, i4, surface);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void moveDisplayToTop(int i) {
        try {
            this.mService.moveDisplayToTop(i);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void launchApplication(int i, String str, Intent intent, Bundle bundle) {
        try {
            this.mService.launchApplication(i, str, intent, bundle);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void transferTaskWithoutIntercept(int i, int i2, Bundle bundle) {
        try {
            this.mService.transferTaskWithoutIntercept(i, i2, bundle);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void transferTaskUsingIntent(Intent intent, int i, int i2, Bundle bundle) {
        try {
            this.mService.transferTaskUsingIntent(intent, i, i2, bundle);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void setLTWProtocolVersion(int i) {
        try {
            this.mService.setLTWProtocolVersion(i);
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

    private static class VirtualDisplayAliveCheckerDelegate extends IVirtualDisplayAliveChecker.Stub {
        private VirtualDisplayAliveChecker mListener;

        VirtualDisplayAliveCheckerDelegate(VirtualDisplayAliveChecker virtualDisplayAliveChecker) {
            this.mListener = virtualDisplayAliveChecker;
        }

        @Override // com.samsung.android.remoteappmode.IVirtualDisplayAliveChecker
        public void onVirtualDisplayCreated(int i) throws RemoteException {
            VirtualDisplayAliveChecker virtualDisplayAliveChecker;
            synchronized (SemRemoteAppModeManager.sLock) {
                virtualDisplayAliveChecker = this.mListener;
            }
            if (virtualDisplayAliveChecker != null) {
                Log.d(SemRemoteAppModeManager.TAG, "onVirtualDisplayCreated() displayId=" + i);
                virtualDisplayAliveChecker.onVirtualDisplayCreated(i);
            }
        }

        @Override // com.samsung.android.remoteappmode.IVirtualDisplayAliveChecker
        public void onVirtualDisplayReleased(int i) throws RemoteException {
            VirtualDisplayAliveChecker virtualDisplayAliveChecker;
            synchronized (SemRemoteAppModeManager.sLock) {
                virtualDisplayAliveChecker = this.mListener;
            }
            if (virtualDisplayAliveChecker != null) {
                Log.d(SemRemoteAppModeManager.TAG, "onVirtualDisplayReleased() displayId=" + i);
                virtualDisplayAliveChecker.onVirtualDisplayReleased(i);
            }
        }

        public String toString() {
            String strValueOf;
            synchronized (SemRemoteAppModeManager.sLock) {
                strValueOf = String.valueOf(this.mListener);
            }
            return strValueOf;
        }

        void nullOutListenerLocked() {
            synchronized (SemRemoteAppModeManager.sLock) {
                this.mListener = null;
            }
        }
    }

    private static class TaskChangeListenerDelegate extends ITaskChangeListener.Stub {
        private TaskChangeListener mListener;

        TaskChangeListenerDelegate(TaskChangeListener taskChangeListener) {
            this.mListener = taskChangeListener;
        }

        @Override // com.samsung.android.remoteappmode.ITaskChangeListener
        public void onTaskRemoved(int i) throws RemoteException {
            TaskChangeListener taskChangeListener;
            synchronized (SemRemoteAppModeManager.sLock) {
                taskChangeListener = this.mListener;
            }
            if (taskChangeListener != null) {
                Log.d(SemRemoteAppModeManager.TAG, "onTaskRemoved() taskId=" + i + ", listener=" + taskChangeListener);
                taskChangeListener.onTaskRemoved(i);
            }
        }

        @Override // com.samsung.android.remoteappmode.ITaskChangeListener
        public void onTaskPlayed(int i, int i2) throws RemoteException {
            TaskChangeListener taskChangeListener;
            synchronized (SemRemoteAppModeManager.sLock) {
                taskChangeListener = this.mListener;
            }
            if (taskChangeListener != null) {
                Log.d(SemRemoteAppModeManager.TAG, "onTaskPlayed() taskId=" + i + ", listener=" + taskChangeListener + ", displayId=" + i2);
                taskChangeListener.onTaskPlayed(i, i2);
            }
        }

        @Override // com.samsung.android.remoteappmode.ITaskChangeListener
        public void onTaskTriedToGoToBackground(int i, int i2) throws RemoteException {
            TaskChangeListener taskChangeListener;
            synchronized (SemRemoteAppModeManager.sLock) {
                taskChangeListener = this.mListener;
            }
            if (taskChangeListener != null) {
                Log.d(SemRemoteAppModeManager.TAG, "onTaskTriedToGoToBackground() taskId=" + i + ", listener=" + taskChangeListener + ", displayId=" + i2);
                taskChangeListener.onTaskTriedToGoToBackground(i, i2);
            }
        }

        @Override // com.samsung.android.remoteappmode.ITaskChangeListener
        public void onTaskDisplayChanged(int i, int i2) throws RemoteException {
            TaskChangeListener taskChangeListener;
            synchronized (SemRemoteAppModeManager.sLock) {
                taskChangeListener = this.mListener;
            }
            if (taskChangeListener != null) {
                Log.d(SemRemoteAppModeManager.TAG, "onTaskDisplayChanged() taskId=" + i + ", listener=" + taskChangeListener + ", displayId=" + i2);
                taskChangeListener.onTaskDisplayChanged(i, i2);
            }
        }

        @Override // com.samsung.android.remoteappmode.ITaskChangeListener
        public void onRecentTaskListUpdated() throws RemoteException {
            TaskChangeListener taskChangeListener;
            synchronized (SemRemoteAppModeManager.sLock) {
                taskChangeListener = this.mListener;
            }
            if (taskChangeListener != null) {
                Log.d(SemRemoteAppModeManager.TAG, "onTaskStackUpdated()");
                taskChangeListener.onRecentTaskListUpdated();
            }
        }

        public String toString() {
            String strValueOf;
            synchronized (SemRemoteAppModeManager.sLock) {
                strValueOf = String.valueOf(this.mListener);
            }
            return strValueOf;
        }

        void nullOutListenerLocked() {
            synchronized (SemRemoteAppModeManager.sLock) {
                this.mListener = null;
            }
        }
    }

    public void registerTaskChangeListener(TaskChangeListener taskChangeListener) {
        synchronized (sLock) {
            if (taskChangeListener == null) {
                Log.w(TAG, "registerTaskChangeListener: Listener is null");
                return;
            }
            if (this.mTaskChangeListeners == null) {
                this.mTaskChangeListeners = new ArrayMap();
            }
            if (this.mTaskChangeListeners.containsKey(taskChangeListener)) {
                Log.w(TAG, "registerTaskChangeListener: " + taskChangeListener + " already registered");
                return;
            }
            TaskChangeListenerDelegate taskChangeListenerDelegate = new TaskChangeListenerDelegate(taskChangeListener);
            try {
                this.mService.registerTaskChangeListener(taskChangeListenerDelegate, taskChangeListener.toString());
                this.mTaskChangeListeners.put(taskChangeListener, taskChangeListenerDelegate);
                Log.i(TAG, "registerTaskChangeListener: " + taskChangeListener);
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
        }
    }

    public void unregisterTaskChangeListener(TaskChangeListener taskChangeListener) {
        synchronized (sLock) {
            if (taskChangeListener == null) {
                Log.w(TAG, "unregisterTaskChangeListener: Listener is null");
                return;
            }
            Map<TaskChangeListener, TaskChangeListenerDelegate> map = this.mTaskChangeListeners;
            if (map == null) {
                return;
            }
            TaskChangeListenerDelegate taskChangeListenerDelegateRemove = map.remove(taskChangeListener);
            if (taskChangeListenerDelegateRemove == null) {
                Log.w(TAG, "unregisterTaskChangeListener: " + taskChangeListener + " already unregistered");
                return;
            }
            if (this.mTaskChangeListeners.isEmpty()) {
                this.mTaskChangeListeners = null;
            }
            try {
                this.mService.unregisterTaskChangeListener(taskChangeListenerDelegateRemove);
                Log.i(TAG, "unregisterTaskChangeListener: " + taskChangeListener);
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
            taskChangeListenerDelegateRemove.nullOutListenerLocked();
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

    public void getLastAnr(String str, ParcelFileDescriptor parcelFileDescriptor) {
        try {
            this.mService.getLastAnr(str, parcelFileDescriptor);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void enableSendingUserPresentIntent(String str) {
        try {
            this.mService.enableSendingUserPresentIntent(str);
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

    public void setSendingUserPresentExpiredTime(long j) {
        try {
            this.mService.setSendingUserPresentExpiredTime(j);
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

    public SemRemoteAppModeManager(Context context, IRemoteAppMode iRemoteAppMode) {
        this.mService = iRemoteAppMode;
    }

    private static class RotationChangedListenerDelegate extends IRotationChangeListener.Stub {
        private RotationChangedListener mListener;

        RotationChangedListenerDelegate(RotationChangedListener rotationChangedListener) {
            this.mListener = rotationChangedListener;
        }

        @Override // com.samsung.android.remoteappmode.IRotationChangeListener
        public void onRotationChanged(int i, int i2) throws RemoteException {
            RotationChangedListener rotationChangedListener;
            synchronized (SemRemoteAppModeManager.sLock) {
                rotationChangedListener = this.mListener;
            }
            if (rotationChangedListener != null) {
                Log.d(SemRemoteAppModeManager.TAG, "onRotationChanged() displayId=" + i + ", rotation=" + i2 + ", listener=" + rotationChangedListener);
                rotationChangedListener.onRotationChanged(i, i2);
            }
        }

        public String toString() {
            String strValueOf;
            synchronized (SemRemoteAppModeManager.sLock) {
                strValueOf = String.valueOf(this.mListener);
            }
            return strValueOf;
        }

        void nullOutListenerLocked() {
            synchronized (SemRemoteAppModeManager.sLock) {
                this.mListener = null;
            }
        }
    }

    public void registerRotationChangeListener(RotationChangedListener rotationChangedListener, int i) {
        synchronized (sLock) {
            if (rotationChangedListener == null) {
                Log.w(TAG, "registerRotationChangeListener: Listener is null");
                return;
            }
            if (this.mRotationChangedListeners == null) {
                this.mRotationChangedListeners = new ArrayMap();
            }
            if (this.mRotationChangedListeners.containsKey(rotationChangedListener)) {
                Log.w(TAG, "registerListener: " + rotationChangedListener + " already registered");
                return;
            }
            RotationChangedListenerDelegate rotationChangedListenerDelegate = new RotationChangedListenerDelegate(rotationChangedListener);
            try {
                this.mService.registerRotationChangeListener(rotationChangedListenerDelegate, rotationChangedListener.toString(), i);
                this.mRotationChangedListeners.put(rotationChangedListener, rotationChangedListenerDelegate);
                Log.i(TAG, "registerRotationChangeListener: " + rotationChangedListener);
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
        }
    }

    public void unregisterRotationChangeListener(RotationChangedListener rotationChangedListener) {
        synchronized (sLock) {
            if (rotationChangedListener == null) {
                Log.w(TAG, "unregisterRotationChangeListener: Listener is null");
                return;
            }
            Map<RotationChangedListener, RotationChangedListenerDelegate> map = this.mRotationChangedListeners;
            if (map == null) {
                return;
            }
            RotationChangedListenerDelegate rotationChangedListenerDelegateRemove = map.remove(rotationChangedListener);
            if (rotationChangedListenerDelegateRemove == null) {
                Log.w(TAG, "unregisterRotationChangeListener: " + rotationChangedListener + " already unregistered");
                return;
            }
            if (this.mRotationChangedListeners.isEmpty()) {
                this.mRotationChangedListeners = null;
            }
            try {
                this.mService.unregisterRotationChangeListener(rotationChangedListenerDelegateRemove);
                Log.i(TAG, "unregisterRotationChangeListener: " + rotationChangedListener);
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
            rotationChangedListenerDelegateRemove.nullOutListenerLocked();
        }
    }

    private static class SecureAppChangedListenerDelegate extends ISecureAppChangedListener.Stub {
        private SecureAppChangedListener mListener;

        SecureAppChangedListenerDelegate(SecureAppChangedListener secureAppChangedListener) {
            this.mListener = secureAppChangedListener;
        }

        @Override // com.samsung.android.remoteappmode.ISecureAppChangedListener
        public void onSecuredAppLaunched(int i, String str) throws RemoteException {
            SecureAppChangedListener secureAppChangedListener;
            synchronized (SemRemoteAppModeManager.sLock) {
                secureAppChangedListener = this.mListener;
            }
            if (secureAppChangedListener != null) {
                Log.d(SemRemoteAppModeManager.TAG, "onSecuredAppLaunched() taskId=" + i + ", packageName=" + str + ", listener=" + secureAppChangedListener);
                secureAppChangedListener.onSecuredAppLaunched(i, str);
            }
        }

        @Override // com.samsung.android.remoteappmode.ISecureAppChangedListener
        public void onSecuredAppRemoved(int i, String str) throws RemoteException {
            SecureAppChangedListener secureAppChangedListener;
            synchronized (SemRemoteAppModeManager.sLock) {
                secureAppChangedListener = this.mListener;
            }
            if (secureAppChangedListener != null) {
                Log.d(SemRemoteAppModeManager.TAG, "onSecuredAppRemoved() taskId=" + i + ", packageName=" + str + ", listener=" + secureAppChangedListener);
                secureAppChangedListener.onSecuredAppRemoved(i, str);
            }
        }

        public String toString() {
            String strValueOf;
            synchronized (SemRemoteAppModeManager.sLock) {
                strValueOf = String.valueOf(this.mListener);
            }
            return strValueOf;
        }

        void nullOutListenerLocked() {
            synchronized (SemRemoteAppModeManager.sLock) {
                this.mListener = null;
            }
        }
    }

    public void registerSecureAppChangedListener(SecureAppChangedListener secureAppChangedListener) {
        synchronized (sLock) {
            if (secureAppChangedListener == null) {
                Log.w(TAG, "registerSecureAppChangedListener: Listener is null");
                return;
            }
            if (this.mSecureAppChangedListeners == null) {
                this.mSecureAppChangedListeners = new ArrayMap();
            }
            if (this.mSecureAppChangedListeners.containsKey(secureAppChangedListener)) {
                Log.w(TAG, "registerSecureAppChangedListener: " + secureAppChangedListener + " already registered");
                return;
            }
            SecureAppChangedListenerDelegate secureAppChangedListenerDelegate = new SecureAppChangedListenerDelegate(secureAppChangedListener);
            try {
                this.mService.registerSecureAppChangedListener(secureAppChangedListenerDelegate, secureAppChangedListener.toString());
                this.mSecureAppChangedListeners.put(secureAppChangedListener, secureAppChangedListenerDelegate);
                Log.i(TAG, "registerSecureAppChangedListener: " + secureAppChangedListener);
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
        }
    }

    public void unregisterSecureAppChangedListener(SecureAppChangedListener secureAppChangedListener) {
        synchronized (sLock) {
            if (secureAppChangedListener == null) {
                Log.w(TAG, "unregisterSecureAppChangedListener: Listener is null");
                return;
            }
            Map<SecureAppChangedListener, SecureAppChangedListenerDelegate> map = this.mSecureAppChangedListeners;
            if (map == null) {
                return;
            }
            SecureAppChangedListenerDelegate secureAppChangedListenerDelegateRemove = map.remove(secureAppChangedListener);
            if (secureAppChangedListenerDelegateRemove == null) {
                Log.w(TAG, "unregisterSecureAppChangedListener: " + secureAppChangedListener + " already unregistered");
                return;
            }
            if (this.mSecureAppChangedListeners.isEmpty()) {
                this.mSecureAppChangedListeners = null;
            }
            try {
                this.mService.unregisterSecureAppChangedListener(secureAppChangedListenerDelegateRemove);
                Log.i(TAG, "unregisterSecureAppChangedListener: " + secureAppChangedListener);
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
            secureAppChangedListenerDelegateRemove.nullOutListenerLocked();
        }
    }

    public void clearAll() {
        Map<StartActivityInterceptedListener, StartActivityInterceptedListenerDelegate> map;
        synchronized (sLock) {
            try {
                this.mService.clearAll();
                Map<TaskChangeListener, TaskChangeListenerDelegate> map2 = this.mTaskChangeListeners;
                if (map2 != null) {
                    map2.clear();
                }
                Map<SecureAppChangedListener, SecureAppChangedListenerDelegate> map3 = this.mSecureAppChangedListeners;
                if (map3 != null) {
                    map3.clear();
                }
                map = this.mStartActivityInterceptedListeners;
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
            if (map != null) {
                map.clear();
            }
        }
    }

    private static class StartActivityInterceptedListenerDelegate extends IStartActivityInterceptListener.Stub {
        private StartActivityInterceptedListener mListener;

        StartActivityInterceptedListenerDelegate(StartActivityInterceptedListener startActivityInterceptedListener) {
            this.mListener = startActivityInterceptedListener;
        }

        @Override // com.samsung.android.remoteappmode.IStartActivityInterceptListener
        public void onStartActivityIntercepted(Intent intent, Bundle bundle, ActivityInfo activityInfo, int i, boolean z, int i2, int i3, int i4) throws RemoteException {
            StartActivityInterceptedListener startActivityInterceptedListener;
            synchronized (SemRemoteAppModeManager.sLock) {
                startActivityInterceptedListener = this.mListener;
            }
            if (startActivityInterceptedListener != null) {
                Log.d(SemRemoteAppModeManager.TAG, "onStartActivityIntercepted() interceptedDisplayId=" + i + ", intent=" + intent.toString() + ", listener=" + startActivityInterceptedListener);
                startActivityInterceptedListener.onStartActivityIntercepted(intent, bundle, activityInfo, i, z, i2, i3, i4);
            }
        }

        public String toString() {
            String strValueOf;
            synchronized (SemRemoteAppModeManager.sLock) {
                strValueOf = String.valueOf(this.mListener);
            }
            return strValueOf;
        }

        void nullOutListenerLocked() {
            synchronized (SemRemoteAppModeManager.sLock) {
                this.mListener = null;
            }
        }
    }

    public void registerStartActivityInterceptedListener(StartActivityInterceptedListener startActivityInterceptedListener) {
        synchronized (sLock) {
            if (startActivityInterceptedListener == null) {
                Log.w(TAG, "registerListener: Listener is null");
                return;
            }
            if (this.mStartActivityInterceptedListeners == null) {
                this.mStartActivityInterceptedListeners = new ArrayMap();
            }
            if (this.mStartActivityInterceptedListeners.containsKey(startActivityInterceptedListener)) {
                Log.w(TAG, "registerListener: " + startActivityInterceptedListener + " already registered");
                return;
            }
            StartActivityInterceptedListenerDelegate startActivityInterceptedListenerDelegate = new StartActivityInterceptedListenerDelegate(startActivityInterceptedListener);
            try {
                this.mService.registerStartActivityInterceptListener(startActivityInterceptedListenerDelegate, startActivityInterceptedListener.toString());
                this.mStartActivityInterceptedListeners.put(startActivityInterceptedListener, startActivityInterceptedListenerDelegate);
                Log.i(TAG, "registerListener: " + startActivityInterceptedListener);
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
        }
    }

    public void unregisterStartActivityInterceptedListener(StartActivityInterceptedListener startActivityInterceptedListener) {
        synchronized (sLock) {
            if (startActivityInterceptedListener == null) {
                Log.w(TAG, "unregisterListener: Listener is null");
                return;
            }
            Map<StartActivityInterceptedListener, StartActivityInterceptedListenerDelegate> map = this.mStartActivityInterceptedListeners;
            if (map == null) {
                return;
            }
            StartActivityInterceptedListenerDelegate startActivityInterceptedListenerDelegateRemove = map.remove(startActivityInterceptedListener);
            if (startActivityInterceptedListenerDelegateRemove == null) {
                Log.w(TAG, "unregisterListener: " + startActivityInterceptedListener + " already unregistered");
                return;
            }
            if (this.mStartActivityInterceptedListeners.isEmpty()) {
                this.mStartActivityInterceptedListeners = null;
            }
            try {
                this.mService.unregisterStartActivityInterceptListener(startActivityInterceptedListenerDelegateRemove);
                Log.i(TAG, "unregisterListener: " + startActivityInterceptedListener);
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
            startActivityInterceptedListenerDelegateRemove.nullOutListenerLocked();
        }
    }

    public void sendPendingIntent(PendingIntent pendingIntent) {
        try {
            this.mService.sendPendingIntent(pendingIntent);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public boolean sendNotificationContent(StatusBarNotification statusBarNotification) {
        try {
            return this.mService.sendNotificationContent(statusBarNotification);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return false;
        }
    }

    public boolean sendNotificationAction(StatusBarNotification statusBarNotification, int i, Intent intent) {
        try {
            return this.mService.sendNotificationAction(statusBarNotification, i, intent);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return false;
        }
    }

    public void forceStopPackage(String str) {
        try {
            this.mService.forceStopPackage(str);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    private static class RemoteAppModeListenerDelegate extends IRemoteAppModeListener.Stub {
        private RemoteAppModeListener mListener;

        RemoteAppModeListenerDelegate(RemoteAppModeListener remoteAppModeListener) {
            this.mListener = remoteAppModeListener;
        }

        @Override // com.samsung.android.remoteappmode.IRemoteAppModeListener
        public void onRemoteAppModeStateChanged(boolean z) throws RemoteException {
            RemoteAppModeListener remoteAppModeListener;
            synchronized (SemRemoteAppModeManager.sLock) {
                remoteAppModeListener = this.mListener;
            }
            if (remoteAppModeListener != null) {
                Log.d(SemRemoteAppModeManager.TAG, "onRemoteAppModeStateChanged() isEnabled=" + z + ", listener=" + remoteAppModeListener);
                remoteAppModeListener.onRemoteAppModeStateChanged(z);
            }
        }

        public String toString() {
            String strValueOf;
            synchronized (SemRemoteAppModeManager.sLock) {
                strValueOf = String.valueOf(this.mListener);
            }
            return strValueOf;
        }

        void nullOutListenerLocked() {
            synchronized (SemRemoteAppModeManager.sLock) {
                this.mListener = null;
            }
        }
    }

    public void registerRemoteAppModeListener(RemoteAppModeListener remoteAppModeListener) {
        synchronized (sLock) {
            if (remoteAppModeListener == null) {
                Log.w(TAG, "registerRemoteAppModeListener: Listener is null");
                return;
            }
            if (this.mRemoteAppModeListeners == null) {
                this.mRemoteAppModeListeners = new ArrayMap();
            }
            if (this.mRemoteAppModeListeners.containsKey(remoteAppModeListener)) {
                Log.w(TAG, "registerRemoteAppModeListener: " + remoteAppModeListener + " already registered");
                return;
            }
            RemoteAppModeListenerDelegate remoteAppModeListenerDelegate = new RemoteAppModeListenerDelegate(remoteAppModeListener);
            try {
                this.mService.registerRemoteAppModeListener(remoteAppModeListenerDelegate, remoteAppModeListener.toString());
                this.mRemoteAppModeListeners.put(remoteAppModeListener, remoteAppModeListenerDelegate);
                Log.i(TAG, "registerRemoteAppModeListener: " + remoteAppModeListener);
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
        }
    }

    public void unregisterRemoteAppModeListener(RemoteAppModeListener remoteAppModeListener) {
        synchronized (sLock) {
            if (remoteAppModeListener == null) {
                Log.w(TAG, "unregisterRemoteAppModeListener: Listener is null");
                return;
            }
            Map<RemoteAppModeListener, RemoteAppModeListenerDelegate> map = this.mRemoteAppModeListeners;
            if (map == null) {
                return;
            }
            RemoteAppModeListenerDelegate remoteAppModeListenerDelegateRemove = map.remove(remoteAppModeListener);
            if (remoteAppModeListenerDelegateRemove == null) {
                Log.w(TAG, "unregisterRemoteAppModeListener: " + remoteAppModeListener + " already unregistered");
                return;
            }
            if (this.mRemoteAppModeListeners.isEmpty()) {
                this.mRemoteAppModeListeners = null;
            }
            try {
                this.mService.unregisterRemoteAppModeListener(remoteAppModeListenerDelegateRemove);
                Log.i(TAG, "unregisterRemoteAppModeListener: " + remoteAppModeListener);
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
            remoteAppModeListenerDelegateRemove.nullOutListenerLocked();
        }
    }
}
