package com.android.server.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;

import com.android.server.SystemService;

import java.util.Objects;
import java.util.concurrent.Executor;

public final class GameServiceController {
    public volatile GameServiceConfiguration.GameServiceComponentConfiguration
            mActiveGameServiceComponentConfiguration;
    public volatile String mActiveGameServiceProviderPackage;
    public final Executor mBackgroundExecutor;
    public final Context mContext;
    public volatile SystemService.TargetUser mCurrentForegroundUser;
    public PackageChangedBroadcastReceiver mGameServicePackageChangedReceiver;
    public volatile GameServiceProviderInstanceImpl mGameServiceProviderInstance;
    public final GameServiceProviderInstanceFactoryImpl mGameServiceProviderInstanceFactory;
    public volatile String mGameServiceProviderOverride;
    public final GameServiceProviderSelectorImpl mGameServiceProviderSelector;
    public volatile boolean mHasBootCompleted;
    public final Object mLock = new Object();

    public final class PackageChangedBroadcastReceiver extends BroadcastReceiver {
        public final String mPackageName;

        public PackageChangedBroadcastReceiver(String str) {
            this.mPackageName = str;
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (TextUtils.equals(intent.getData().getSchemeSpecificPart(), this.mPackageName)) {
                GameServiceController gameServiceController = GameServiceController.this;
                gameServiceController.mBackgroundExecutor.execute(
                        new GameServiceController$$ExternalSyntheticLambda0(gameServiceController));
            }
        }
    }

    public GameServiceController(
            Context context,
            Executor executor,
            GameServiceProviderSelectorImpl gameServiceProviderSelectorImpl,
            GameServiceProviderInstanceFactoryImpl gameServiceProviderInstanceFactoryImpl) {
        this.mContext = context;
        this.mGameServiceProviderInstanceFactory = gameServiceProviderInstanceFactoryImpl;
        this.mBackgroundExecutor = executor;
        this.mGameServiceProviderSelector = gameServiceProviderSelectorImpl;
    }

    public final void evaluateGameServiceProviderPackageChangedListenerLocked(String str) {
        if (TextUtils.equals(this.mActiveGameServiceProviderPackage, str)) {
            return;
        }
        PackageChangedBroadcastReceiver packageChangedBroadcastReceiver =
                this.mGameServicePackageChangedReceiver;
        if (packageChangedBroadcastReceiver != null) {
            this.mContext.unregisterReceiver(packageChangedBroadcastReceiver);
            this.mGameServicePackageChangedReceiver = null;
        }
        this.mActiveGameServiceProviderPackage = str;
        if (TextUtils.isEmpty(this.mActiveGameServiceProviderPackage)) {
            return;
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addDataScheme("package");
        intentFilter.addDataSchemeSpecificPart(str, 0);
        PackageChangedBroadcastReceiver packageChangedBroadcastReceiver2 =
                new PackageChangedBroadcastReceiver(str);
        this.mGameServicePackageChangedReceiver = packageChangedBroadcastReceiver2;
        this.mContext.registerReceiver(packageChangedBroadcastReceiver2, intentFilter);
    }

    public final void setCurrentForegroundUserAndEvaluateProvider(
            SystemService.TargetUser targetUser) {
        if (!Objects.equals(this.mCurrentForegroundUser, targetUser)) {
            this.mCurrentForegroundUser = targetUser;
            this.mBackgroundExecutor.execute(
                    new GameServiceController$$ExternalSyntheticLambda0(this));
        }
    }
}
