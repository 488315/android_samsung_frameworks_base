package android.app;

import android.annotation.SystemApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.RemoteException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes.dex */
public final class GameManager {
    public static final int GAME_MODE_BATTERY = 3;
    public static final int GAME_MODE_CUSTOM = 4;
    public static final int GAME_MODE_PERFORMANCE = 2;
    public static final int GAME_MODE_STANDARD = 1;
    public static final int GAME_MODE_UNSUPPORTED = 0;
    private static final String TAG = "GameManager";
    private final Context mContext;
    private final IGameManagerService mService;

    @Retention(RetentionPolicy.SOURCE)
    public @interface GameMode {
    }

    GameManager(Context context, IGameManagerService iGameManagerService) {
        this.mContext = context;
        this.mService = iGameManagerService;
    }

    public int getGameMode() {
        return getGameModeImpl(this.mContext.getPackageName(), this.mContext.getApplicationInfo().targetSdkVersion);
    }

    public int getGameMode(String str) {
        try {
            return getGameModeImpl(str, this.mContext.getPackageManager().getApplicationInfo(str, PackageManager.ApplicationInfoFlags.of(0L)).targetSdkVersion);
        } catch (PackageManager.NameNotFoundException unused) {
            return 0;
        }
    }

    private int getGameModeImpl(String str, int i) {
        IGameManagerService iGameManagerService = this.mService;
        if (iGameManagerService == null) {
            return 0;
        }
        try {
            int gameMode = iGameManagerService.getGameMode(str, this.mContext.getUserId());
            if (gameMode != 4 || i > 33) {
                return gameMode;
            }
            return 1;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public GameModeInfo getGameModeInfo(String str) {
        IGameManagerService iGameManagerService = this.mService;
        if (iGameManagerService == null) {
            return null;
        }
        try {
            return iGameManagerService.getGameModeInfo(str, this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void setGameMode(String str, int i) {
        IGameManagerService iGameManagerService = this.mService;
        if (iGameManagerService == null) {
            return;
        }
        try {
            iGameManagerService.setGameMode(str, i, this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int[] getAvailableGameModes(String str) {
        IGameManagerService iGameManagerService = this.mService;
        if (iGameManagerService == null) {
            return new int[0];
        }
        try {
            return iGameManagerService.getAvailableGameModes(str, this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isAngleEnabled(String str) {
        IGameManagerService iGameManagerService = this.mService;
        if (iGameManagerService == null) {
            return false;
        }
        try {
            return iGameManagerService.isAngleEnabled(str, this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyGraphicsEnvironmentSetup() {
        IGameManagerService iGameManagerService = this.mService;
        if (iGameManagerService == null) {
            return;
        }
        try {
            iGameManagerService.notifyGraphicsEnvironmentSetup(this.mContext.getPackageName(), this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setGameState(GameState gameState) {
        IGameManagerService iGameManagerService = this.mService;
        if (iGameManagerService == null) {
            return;
        }
        try {
            iGameManagerService.setGameState(this.mContext.getPackageName(), gameState, this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setGameServiceProvider(String str) {
        IGameManagerService iGameManagerService = this.mService;
        if (iGameManagerService == null) {
            return;
        }
        try {
            iGameManagerService.setGameServiceProvider(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void updateCustomGameModeConfiguration(String str, GameModeConfiguration gameModeConfiguration) {
        IGameManagerService iGameManagerService = this.mService;
        if (iGameManagerService == null) {
            return;
        }
        try {
            iGameManagerService.updateCustomGameModeConfiguration(str, gameModeConfiguration, this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
