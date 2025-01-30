package com.android.server.app;

import android.R;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Slog;
import android.util.Xml;
import com.android.server.SystemService;
import com.android.server.app.GameServiceConfiguration;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public final class GameServiceProviderSelectorImpl implements GameServiceProviderSelector {
    public final PackageManager mPackageManager;
    public final Resources mResources;

    public GameServiceProviderSelectorImpl(Resources resources, PackageManager packageManager) {
        this.mResources = resources;
        this.mPackageManager = packageManager;
    }

    @Override // com.android.server.app.GameServiceProviderSelector
    public GameServiceConfiguration get(SystemService.TargetUser targetUser, String str) {
        GameServiceConfiguration gameServiceConfiguration;
        ComponentName determineGameSessionServiceFromGameService;
        if (targetUser == null) {
            return null;
        }
        int i = 0;
        if (!(targetUser.isFull() && !targetUser.isManagedProfile())) {
            Slog.i("GameServiceProviderSelector", "Game Service not supported for user: " + targetUser.getUserIdentifier());
            return null;
        }
        if (TextUtils.isEmpty(str)) {
            str = this.mResources.getString(R.string.ext_media_status_checking);
            i = 1048576;
        }
        if (TextUtils.isEmpty(str)) {
            Slog.w("GameServiceProviderSelector", "No game service package defined");
            return null;
        }
        int userIdentifier = targetUser.getUserIdentifier();
        List queryIntentServicesAsUser = this.mPackageManager.queryIntentServicesAsUser(new Intent("android.service.games.action.GAME_SERVICE").setPackage(str), i | 128, userIdentifier);
        if (queryIntentServicesAsUser == null || queryIntentServicesAsUser.isEmpty()) {
            Slog.w("GameServiceProviderSelector", "No available game service found for user id: " + userIdentifier);
            return new GameServiceConfiguration(str, null);
        }
        Iterator it = queryIntentServicesAsUser.iterator();
        while (true) {
            if (!it.hasNext()) {
                gameServiceConfiguration = null;
                break;
            }
            ServiceInfo serviceInfo = ((ResolveInfo) it.next()).serviceInfo;
            if (serviceInfo != null && (determineGameSessionServiceFromGameService = determineGameSessionServiceFromGameService(serviceInfo)) != null) {
                gameServiceConfiguration = new GameServiceConfiguration(str, new GameServiceConfiguration.GameServiceComponentConfiguration(new UserHandle(userIdentifier), serviceInfo.getComponentName(), determineGameSessionServiceFromGameService));
                break;
            }
        }
        if (gameServiceConfiguration != null) {
            return gameServiceConfiguration;
        }
        Slog.w("GameServiceProviderSelector", "No valid game service found for user id: " + userIdentifier);
        return new GameServiceConfiguration(str, null);
    }

    public final ComponentName determineGameSessionServiceFromGameService(ServiceInfo serviceInfo) {
        int next;
        try {
            XmlResourceParser loadXmlMetaData = serviceInfo.loadXmlMetaData(this.mPackageManager, "android.game_service");
            try {
                if (loadXmlMetaData == null) {
                    Slog.w("GameServiceProviderSelector", "No android.game_service meta-data found for " + serviceInfo.getComponentName());
                    if (loadXmlMetaData != null) {
                        loadXmlMetaData.close();
                    }
                    return null;
                }
                Resources resourcesForApplication = this.mPackageManager.getResourcesForApplication(serviceInfo.packageName);
                AttributeSet asAttributeSet = Xml.asAttributeSet(loadXmlMetaData);
                do {
                    next = loadXmlMetaData.next();
                    if (next == 1) {
                        break;
                    }
                } while (next != 2);
                if (!"game-service".equals(loadXmlMetaData.getName())) {
                    Slog.w("GameServiceProviderSelector", "Meta-data does not start with game-service tag");
                    loadXmlMetaData.close();
                    return null;
                }
                TypedArray obtainAttributes = resourcesForApplication.obtainAttributes(asAttributeSet, com.android.internal.R.styleable.GameService);
                String string = obtainAttributes.getString(0);
                obtainAttributes.recycle();
                loadXmlMetaData.close();
                if (TextUtils.isEmpty(string)) {
                    Slog.w("GameServiceProviderSelector", "No gameSessionService specified");
                    return null;
                }
                ComponentName componentName = new ComponentName(serviceInfo.packageName, string);
                try {
                    this.mPackageManager.getServiceInfo(componentName, 0);
                    return componentName;
                } catch (PackageManager.NameNotFoundException unused) {
                    Slog.w("GameServiceProviderSelector", "GameSessionService does not exist: " + componentName);
                    return null;
                }
            } catch (Throwable th) {
                if (loadXmlMetaData != null) {
                    try {
                        loadXmlMetaData.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (PackageManager.NameNotFoundException | IOException | XmlPullParserException e) {
            Slog.w("Error while parsing meta-data for " + serviceInfo.getComponentName(), e);
            return null;
        }
    }
}
