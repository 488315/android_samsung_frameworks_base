package com.samsung.android.knox;

import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.ContainerStateReceiver;

/* loaded from: classes6.dex */
public class SemContainerState {
    public static String ACTION_CONTAINER_STATE_RECEIVER = "com.samsung.android.knox.ACTION_CONTAINER_STATE_RECEIVER";
    private static final int CONTAINER_MODE_LAUNCHER = 1;
    private static final int CONTAINER_MODE_PERSONAL = 0;
    private static boolean DEBUG = false;
    private static String TAG = "SemContainerState";
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
        @Override // android.os.ContainerStateReceiver
        public void onContainerReset(Context context, int i, Bundle bundle) {
        }

        @Override // android.os.ContainerStateReceiver
        public void onDeviceOwnerActivated(Context context, Bundle bundle) {
        }

        @Override // android.os.ContainerStateReceiver
        public void onDeviceOwnerLicenseActivated(Context context, Bundle bundle) {
        }

        private StateReceiver() {
        }

        @Override // android.os.ContainerStateReceiver
        public void onContainerCreated(Context context, int i, Bundle bundle) {
            if (SemContainerState.this.mStateListener != null) {
                SemContainerState.this.mStateListener.onContainerCreated(context, i);
            }
        }

        @Override // android.os.ContainerStateReceiver
        public void onContainerRemoved(Context context, int i, Bundle bundle) {
            if (SemContainerState.this.mStateListener != null) {
                SemContainerState.this.mStateListener.onContainerRemoved(context, i);
            }
        }

        @Override // android.os.ContainerStateReceiver
        public void onContainerEnabled(Context context, int i, Bundle bundle) {
            if (SemContainerState.this.mStateListener != null) {
                SemContainerState.this.mStateListener.onContainerEnabled(context, i);
            }
        }

        @Override // android.os.ContainerStateReceiver
        public void onContainerRunning(Context context, int i, Bundle bundle) {
            if (SemContainerState.this.mStateListener != null) {
                SemContainerState.this.mStateListener.onContainerStarted(context, i);
            }
        }

        @Override // android.os.ContainerStateReceiver
        public void onContainerShutdown(Context context, int i, Bundle bundle) {
            if (SemContainerState.this.mStateListener != null) {
                SemContainerState.this.mStateListener.onContainerStopped(context, i);
            }
        }

        @Override // android.os.ContainerStateReceiver
        public void onContainerLocked(Context context, int i, Bundle bundle) {
            if (SemContainerState.this.mLockListener != null) {
                SemContainerState.this.mLockListener.onUserLocked(context, i);
            }
        }

        @Override // android.os.ContainerStateReceiver
        public void onContainerUnlocked(Context context, int i, Bundle bundle) {
            if (SemContainerState.this.mLockListener != null) {
                SemContainerState.this.mLockListener.onUserUnlocked(context, i);
            }
        }

        @Override // android.os.ContainerStateReceiver
        public void onContainerAdminLocked(Context context, int i, Bundle bundle) {
            if (SemContainerState.this.mLockListener != null) {
                SemContainerState.this.mLockListener.onAdminLocked(context, i);
            }
        }

        @Override // android.os.ContainerStateReceiver
        public void onContainerAdminUnlocked(Context context, int i, Bundle bundle) {
            if (SemContainerState.this.mLockListener != null) {
                SemContainerState.this.mLockListener.onAdminUnlocked(context, i);
            }
        }

        @Override // android.os.ContainerStateReceiver
        public void onLicenseActivated(Context context, int i, Bundle bundle) {
            if (SemContainerState.this.mLockListener != null) {
                SemContainerState.this.mLockListener.onLicenseActivated(context, i);
            }
        }

        @Override // android.os.ContainerStateReceiver
        public void onLicenseExpired(Context context, int i, Bundle bundle) {
            if (SemContainerState.this.mLockListener != null) {
                SemContainerState.this.mLockListener.onLicenseExpired(context, i);
            }
        }

        @Override // android.os.ContainerStateReceiver
        public void onLockScreenStateChanged(Context context, int i, boolean z, Bundle bundle) {
            if (SemContainerState.this.mEventListener != null) {
                SemContainerState.this.mEventListener.onLockScreenVisivilityChanged(context, i, z);
            }
        }

        @Override // android.os.ContainerStateReceiver
        public void onContainerSwitch(Context context, int i, Bundle bundle) {
            if (SemContainerState.this.mEventListener != null) {
                SemContainerState.this.mEventListener.onContainerModeChanged(context, 1, i);
            }
        }

        @Override // android.os.ContainerStateReceiver
        public void onPersonalSwitch(Context context, Bundle bundle) {
            if (SemContainerState.this.mEventListener != null) {
                SemContainerState.this.mEventListener.onContainerModeChanged(context, 0, 0);
            }
        }
    }

    public void register(Context context, StateListener stateListener, LockListener lockListener, EventListener eventListener) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_CONTAINER_STATE_RECEIVER);
        this.mLockListener = lockListener;
        this.mStateListener = stateListener;
        this.mEventListener = eventListener;
        StateReceiver stateReceiver = new StateReceiver();
        this.mReceiver = stateReceiver;
        context.registerReceiver(stateReceiver, intentFilter);
    }

    public void unregister(Context context) {
        context.unregisterReceiver(this.mReceiver);
        this.mLockListener = null;
        this.mStateListener = null;
        this.mEventListener = null;
        this.mReceiver = null;
    }
}
