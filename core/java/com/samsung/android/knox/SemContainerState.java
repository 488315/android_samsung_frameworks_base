package com.samsung.android.knox;

import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.ContainerStateReceiver;

public class SemContainerState {
    private static final int CONTAINER_MODE_LAUNCHER = 1;
    private static final int CONTAINER_MODE_PERSONAL = 0;
    private static String TAG = "SemContainerState";
    private static boolean DEBUG = false;
    public static String ACTION_CONTAINER_STATE_RECEIVER =
            "com.samsung.android.knox.ACTION_CONTAINER_STATE_RECEIVER";
    private StateReceiver mReceiver = null;
    private StateListener mStateListener = null;
    private LockListener mLockListener = null;
    private EventListener mEventListener = null;

    public interface EventListener {
        void onContainerModeChanged(Context context, int i, int i2);

        void onLockScreenVisivilityChanged(Context context, int i, boolean z);
    }

    public interface LockListener {
        void onAdminLocked(Context context, int i);

        void onAdminUnlocked(Context context, int i);

        void onLicenseActivated(Context context, int i);

        void onLicenseExpired(Context context, int i);

        void onUserLocked(Context context, int i);

        void onUserUnlocked(Context context, int i);
    }

    public interface StateListener {
        void onContainerCreated(Context context, int i);

        void onContainerEnabled(Context context, int i);

        void onContainerRemoved(Context context, int i);

        void onContainerStarted(Context context, int i);

        void onContainerStopped(Context context, int i);
    }

    private class StateReceiver extends ContainerStateReceiver {
        private StateReceiver() {}

        @Override // android.os.ContainerStateReceiver
        public void onContainerCreated(Context context, int userHandle, Bundle b) {
            if (SemContainerState.this.mStateListener != null) {
                SemContainerState.this.mStateListener.onContainerCreated(context, userHandle);
            }
        }

        @Override // android.os.ContainerStateReceiver
        public void onContainerRemoved(Context context, int userHandle, Bundle b) {
            if (SemContainerState.this.mStateListener != null) {
                SemContainerState.this.mStateListener.onContainerRemoved(context, userHandle);
            }
        }

        @Override // android.os.ContainerStateReceiver
        public void onContainerEnabled(Context context, int userHandle, Bundle b) {
            if (SemContainerState.this.mStateListener != null) {
                SemContainerState.this.mStateListener.onContainerEnabled(context, userHandle);
            }
        }

        @Override // android.os.ContainerStateReceiver
        public void onContainerRunning(Context context, int userHandle, Bundle b) {
            if (SemContainerState.this.mStateListener != null) {
                SemContainerState.this.mStateListener.onContainerStarted(context, userHandle);
            }
        }

        @Override // android.os.ContainerStateReceiver
        public void onContainerShutdown(Context context, int userHandle, Bundle b) {
            if (SemContainerState.this.mStateListener != null) {
                SemContainerState.this.mStateListener.onContainerStopped(context, userHandle);
            }
        }

        @Override // android.os.ContainerStateReceiver
        public void onContainerLocked(Context context, int userHandle, Bundle b) {
            if (SemContainerState.this.mLockListener != null) {
                SemContainerState.this.mLockListener.onUserLocked(context, userHandle);
            }
        }

        @Override // android.os.ContainerStateReceiver
        public void onContainerUnlocked(Context context, int userHandle, Bundle b) {
            if (SemContainerState.this.mLockListener != null) {
                SemContainerState.this.mLockListener.onUserUnlocked(context, userHandle);
            }
        }

        @Override // android.os.ContainerStateReceiver
        public void onContainerAdminLocked(Context context, int userHandle, Bundle b) {
            if (SemContainerState.this.mLockListener != null) {
                SemContainerState.this.mLockListener.onAdminLocked(context, userHandle);
            }
        }

        @Override // android.os.ContainerStateReceiver
        public void onContainerAdminUnlocked(Context context, int userHandle, Bundle b) {
            if (SemContainerState.this.mLockListener != null) {
                SemContainerState.this.mLockListener.onAdminUnlocked(context, userHandle);
            }
        }

        @Override // android.os.ContainerStateReceiver
        public void onLicenseActivated(Context context, int userHandle, Bundle b) {
            if (SemContainerState.this.mLockListener != null) {
                SemContainerState.this.mLockListener.onLicenseActivated(context, userHandle);
            }
        }

        @Override // android.os.ContainerStateReceiver
        public void onLicenseExpired(Context context, int userHandle, Bundle b) {
            if (SemContainerState.this.mLockListener != null) {
                SemContainerState.this.mLockListener.onLicenseExpired(context, userHandle);
            }
        }

        @Override // android.os.ContainerStateReceiver
        public void onLockScreenStateChanged(
                Context context, int userHandle, boolean visible, Bundle b) {
            if (SemContainerState.this.mEventListener != null) {
                SemContainerState.this.mEventListener.onLockScreenVisivilityChanged(
                        context, userHandle, visible);
            }
        }

        @Override // android.os.ContainerStateReceiver
        public void onContainerSwitch(Context context, int userHandle, Bundle b) {
            if (SemContainerState.this.mEventListener != null) {
                SemContainerState.this.mEventListener.onContainerModeChanged(
                        context, 1, userHandle);
            }
        }

        @Override // android.os.ContainerStateReceiver
        public void onPersonalSwitch(Context context, Bundle b) {
            if (SemContainerState.this.mEventListener != null) {
                SemContainerState.this.mEventListener.onContainerModeChanged(context, 0, 0);
            }
        }

        @Override // android.os.ContainerStateReceiver
        public void onContainerReset(Context context, int userHandle, Bundle b) {}

        @Override // android.os.ContainerStateReceiver
        public void onDeviceOwnerActivated(Context context, Bundle b) {}

        @Override // android.os.ContainerStateReceiver
        public void onDeviceOwnerLicenseActivated(Context context, Bundle b) {}
    }

    public void register(
            Context context,
            StateListener stateListener,
            LockListener lockListener,
            EventListener eventListener) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_CONTAINER_STATE_RECEIVER);
        this.mLockListener = lockListener;
        this.mStateListener = stateListener;
        this.mEventListener = eventListener;
        this.mReceiver = new StateReceiver();
        context.registerReceiver(this.mReceiver, intentFilter);
    }

    public void unregister(Context context) {
        context.unregisterReceiver(this.mReceiver);
        this.mLockListener = null;
        this.mStateListener = null;
        this.mEventListener = null;
        this.mReceiver = null;
    }
}
