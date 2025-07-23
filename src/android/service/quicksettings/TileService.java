package android.service.quicksettings;

import android.annotation.SystemApi;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.Service;
import android.app.StatusBarManager;
import android.app.compat.CompatChanges;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Icon;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.service.quicksettings.IQSService;
import android.service.quicksettings.IQSTileService;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.RemoteViews;
import com.android.internal.R;
import java.util.Objects;

/* loaded from: classes3.dex */
public class TileService extends Service {
    public static final String ACTION_QS_TILE = "android.service.quicksettings.action.QS_TILE";
    public static final String ACTION_QS_TILE_PREFERENCES = "android.service.quicksettings.action.QS_TILE_PREFERENCES";
    private static final boolean DEBUG = false;
    public static final String EXTRA_SERVICE = "service";
    public static final String EXTRA_STATE = "state";
    public static final String EXTRA_TOKEN = "token";
    public static final String META_DATA_ACTIVE_TILE = "android.service.quicksettings.ACTIVE_TILE";
    public static final String META_DATA_TOGGLEABLE_TILE = "android.service.quicksettings.TOGGLEABLE_TILE";
    public static final String SEM_META_DATA_ACTIVE_TILE_SUPPORT_SEM_PLATFORM_VER = "android.service.quicksettings.SEM_ACTIVE_TILE_SUPPORT_SEM_PLATFORM_VER";
    public static final String SEM_META_DATA_DEFAULT_TILE_DEXMODE_ONLY = "android.service.quicksettings.SEM_DEFAULT_TILE_DEXMODE_ONLY";
    public static final String SEM_META_DATA_DEFAULT_TILE_NAME = "android.service.quicksettings.SEM_DEFAULT_TILE_NAME";
    public static final String SEM_META_DATA_DEFAULT_TILE_STATE = "android.service.quicksettings.SEM_DEFAULT_TILE_STATE";
    public static final String SEM_META_DATA_DEFAULT_TILE_UNLOCK_POLICY = "android.service.quicksettings.SEM_DEFAULT_TILE_UNLOCK_POLICY";
    public static final String SEM_META_DATA_DEFAULT_TILE_USER_POLICY = "android.service.quicksettings.SEM_DEFAULT_TILE_USER_POLICY";
    public static final String SEM_META_DATA_SUPPORT_DETAIL_VIEW = "android.service.quicksettings.SEM_SUPPORT_DETAIL_VIEW";
    public static final long START_ACTIVITY_NEEDS_PENDING_INTENT = 241766793;
    private static final String TAG = "TileService";
    private final H mHandler = new H(Looper.getMainLooper());
    private boolean mListening = false;
    private IQSService mService;
    private Tile mTile;
    private IBinder mTileToken;
    private IBinder mToken;
    private Runnable mUnlockRunnable;

    public void onClick() {
    }

    public void onStartListening() {
    }

    public void onStopListening() {
    }

    public void onTileAdded() {
    }

    public void onTileRemoved() {
    }

    public RemoteViews semGetDetailView() {
        return null;
    }

    public CharSequence semGetDetailViewTitle() {
        return null;
    }

    public Intent semGetSettingsIntent() {
        return null;
    }

    public boolean semIsToggleButtonChecked() {
        return false;
    }

    public boolean semIsToggleButtonExists() {
        return true;
    }

    public void semSetToggleButtonChecked(boolean z) {
    }

    @Override // android.app.Service
    public void onDestroy() {
        if (this.mListening) {
            onStopListening();
            this.mListening = false;
        }
        super.onDestroy();
    }

    @SystemApi
    public final void setStatusIcon(Icon icon, String str) {
        IQSService iQSService = this.mService;
        if (iQSService != null) {
            try {
                iQSService.updateStatusIcon(this.mTileToken, icon, str);
            } catch (RemoteException unused) {
            }
        }
    }

    public final void showDialog(Dialog dialog) {
        WindowManager.LayoutParams attributes = dialog.getWindow().getAttributes();
        if (attributes.type != 2008 && attributes.type != 2009) {
            dialog.getWindow().getAttributes().token = this.mToken;
            dialog.getWindow().setType(2035);
        }
        dialog.getWindow().getDecorView().addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: android.service.quicksettings.TileService.1
            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewAttachedToWindow(View view) {
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewDetachedFromWindow(View view) {
                try {
                    TileService.this.mService.onDialogHidden(TileService.this.mTileToken);
                } catch (RemoteException unused) {
                }
            }
        });
        dialog.show();
        try {
            this.mService.onShowDialog(this.mTileToken);
        } catch (RemoteException unused) {
        }
    }

    public final void unlockAndRun(Runnable runnable) {
        this.mUnlockRunnable = runnable;
        try {
            this.mService.startUnlockAndRun(this.mTileToken);
        } catch (RemoteException unused) {
        }
    }

    public final boolean isSecure() {
        try {
            return this.mService.isSecure();
        } catch (RemoteException unused) {
            return true;
        }
    }

    public final boolean isLocked() {
        try {
            return this.mService.isLocked();
        } catch (RemoteException unused) {
            return true;
        }
    }

    @Deprecated
    public final void startActivityAndCollapse(Intent intent) {
        if (CompatChanges.isChangeEnabled(START_ACTIVITY_NEEDS_PENDING_INTENT)) {
            throw new UnsupportedOperationException("startActivityAndCollapse: Starting activity from TileService using an Intent is not allowed.");
        }
        startActivity(intent);
        try {
            this.mService.onStartActivity(this.mTileToken);
        } catch (RemoteException unused) {
        }
    }

    public final void startActivityAndCollapse(PendingIntent pendingIntent) {
        Objects.requireNonNull(pendingIntent);
        try {
            this.mService.startActivity(this.mTileToken, pendingIntent);
        } catch (RemoteException unused) {
        }
    }

    public final void semUpdateDetailView() {
        try {
            this.mService.semUpdateDetailView(this.mTileToken);
        } catch (RemoteException unused) {
        }
    }

    public final void semFireToggleStateChanged(boolean z, boolean z2) {
        try {
            this.mService.semFireToggleStateChanged(this.mTileToken, z, z2);
        } catch (RemoteException unused) {
        }
    }

    public final Tile getQsTile() {
        IQSService iQSService;
        IBinder iBinder;
        if (this.mTile == null && (iQSService = this.mService) != null && (iBinder = this.mTileToken) != null) {
            try {
                this.mTile = iQSService.getTile(iBinder);
            } catch (RemoteException e) {
                if (e instanceof DeadObjectException) {
                    Log.d(TAG, "getQsTile : Unable to reach IQSService : " + e);
                } else {
                    throw new RuntimeException("getQsTile : Unable to reach IQSService", e);
                }
            }
            Log.d(TAG, "getQsTile : mTile = " + this.mTile);
            Tile tile = this.mTile;
            if (tile != null) {
                tile.setService(this.mService, this.mTileToken);
                this.mHandler.sendEmptyMessage(7);
            }
        }
        return this.mTile;
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        this.mService = IQSService.Stub.asInterface(intent.getIBinderExtra("service"));
        this.mTileToken = intent.getIBinderExtra("token");
        Log.d(TAG, "onBind : mService = " + this.mService + ", mTileToken = " + this.mTileToken + getClass().getSimpleName());
        try {
            this.mTile = this.mService.getTile(this.mTileToken);
            Log.d(TAG, "onBind : mTile = " + this.mTile);
            Tile tile = this.mTile;
            if (tile != null) {
                tile.setService(this.mService, this.mTileToken);
                this.mHandler.sendEmptyMessage(7);
            }
            return new IQSTileService.Stub() { // from class: android.service.quicksettings.TileService.2
                @Override // android.service.quicksettings.IQSTileService
                public void onTileRemoved() throws RemoteException {
                    TileService.this.mHandler.sendEmptyMessage(4);
                }

                @Override // android.service.quicksettings.IQSTileService
                public void onTileAdded() throws RemoteException {
                    TileService.this.mHandler.sendEmptyMessage(3);
                }

                @Override // android.service.quicksettings.IQSTileService
                public void onStopListening() throws RemoteException {
                    TileService.this.mHandler.sendEmptyMessage(2);
                }

                @Override // android.service.quicksettings.IQSTileService
                public void onStartListening() throws RemoteException {
                    TileService.this.mHandler.sendEmptyMessage(1);
                }

                @Override // android.service.quicksettings.IQSTileService
                public void onClick(IBinder iBinder) throws RemoteException {
                    TileService.this.mHandler.obtainMessage(5, iBinder).sendToTarget();
                }

                @Override // android.service.quicksettings.IQSTileService
                public void onUnlockComplete() throws RemoteException {
                    TileService.this.mHandler.sendEmptyMessage(6);
                }

                @Override // android.service.quicksettings.IQSTileService
                public CharSequence semGetDetailViewTitle() throws RemoteException {
                    return TileService.this.semGetDetailViewTitle();
                }

                @Override // android.service.quicksettings.IQSTileService
                public boolean semIsToggleButtonExists() throws RemoteException {
                    return TileService.this.semIsToggleButtonExists();
                }

                @Override // android.service.quicksettings.IQSTileService
                public boolean semIsToggleButtonChecked() throws RemoteException {
                    return TileService.this.semIsToggleButtonChecked();
                }

                @Override // android.service.quicksettings.IQSTileService
                public RemoteViews semGetDetailView() throws RemoteException {
                    return TileService.this.semGetDetailView();
                }

                @Override // android.service.quicksettings.IQSTileService
                public Intent semGetSettingsIntent() throws RemoteException {
                    return TileService.this.semGetSettingsIntent();
                }

                @Override // android.service.quicksettings.IQSTileService
                public void semSetToggleButtonChecked(boolean z) throws RemoteException {
                    TileService.this.mHandler.obtainMessage(8, Boolean.valueOf(z)).sendToTarget();
                }
            };
        } catch (RemoteException e) {
            Log.w(TAG, getClass().getSimpleName() + " - Couldn't get tile from IQSService.", e);
            return null;
        }
    }

    private class H extends Handler {
        private static final int MSG_SET_TOGGLE = 8;
        private static final int MSG_START_LISTENING = 1;
        private static final int MSG_START_SUCCESS = 7;
        private static final int MSG_STOP_LISTENING = 2;
        private static final int MSG_TILE_ADDED = 3;
        private static final int MSG_TILE_CLICKED = 5;
        private static final int MSG_TILE_REMOVED = 4;
        private static final int MSG_UNLOCK_COMPLETE = 6;
        private final String mTileServiceName;

        public H(Looper looper) {
            super(looper);
            this.mTileServiceName = TileService.this.getClass().getSimpleName();
        }

        private void logMessage(String str) {
            Log.d(TileService.TAG, this.mTileServiceName + " Handler - " + str);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    if (!TileService.this.mListening) {
                        TileService.this.mListening = true;
                        TileService.this.onStartListening();
                        break;
                    }
                    break;
                case 2:
                    if (TileService.this.mListening) {
                        TileService.this.mListening = false;
                        TileService.this.onStopListening();
                        break;
                    }
                    break;
                case 3:
                    TileService.this.onTileAdded();
                    break;
                case 4:
                    if (TileService.this.mListening) {
                        TileService.this.mListening = false;
                        TileService.this.onStopListening();
                    }
                    TileService.this.onTileRemoved();
                    break;
                case 5:
                    TileService.this.mToken = (IBinder) message.obj;
                    TileService.this.onClick();
                    break;
                case 6:
                    if (TileService.this.mUnlockRunnable != null) {
                        TileService.this.mUnlockRunnable.run();
                        break;
                    }
                    break;
                case 7:
                    try {
                        TileService.this.mService.onStartSuccessful(TileService.this.mTileToken);
                        break;
                    } catch (RemoteException e) {
                        logMessage("MSG_START_SUCCESS : " + e);
                        return;
                    }
                case 8:
                    TileService.this.semSetToggleButtonChecked(((Boolean) message.obj).booleanValue());
                    break;
            }
        }
    }

    public static boolean isQuickSettingsSupported() {
        return Resources.getSystem().getBoolean(R.bool.config_quickSettingsSupported);
    }

    public static final void requestListeningState(Context context, ComponentName componentName) {
        StatusBarManager statusBarManager = (StatusBarManager) context.getSystemService(StatusBarManager.class);
        if (statusBarManager == null) {
            Log.e(TAG, "No StatusBarManager service found");
        } else {
            statusBarManager.requestTileServiceListeningState(componentName);
        }
    }
}
