package com.android.server.am.mars.filter.filter;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.util.Slog;
import com.android.server.am.MARsPolicyManager;
import com.android.server.am.mars.filter.IFilter;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/* loaded from: classes.dex */
public class EdgeAppFilter implements IFilter {
  public final String TAG;
  public ArrayList mCocktailBarList;
  ContentObserver mCocktailBarSettingsObserver;
  public Context mContext;
  public boolean mRegisteredCocktailBarSettingsObserver;

  public abstract class EdgeAppFilterHolder {
    public static final EdgeAppFilter INSTANCE = new EdgeAppFilter();
  }

  public EdgeAppFilter() {
    this.TAG = "MARs:" + EdgeAppFilter.class.getSimpleName();
    this.mCocktailBarList = new ArrayList();
    this.mContext = null;
    this.mCocktailBarSettingsObserver = null;
    this.mRegisteredCocktailBarSettingsObserver = false;
  }

  public static EdgeAppFilter getInstance() {
    return EdgeAppFilterHolder.INSTANCE;
  }

  public final void setContext(Context context) {
    this.mContext = context;
  }

  @Override // com.android.server.am.mars.filter.IFilter
  public void init(Context context) {
    setContext(context);
    registerEnabledCocktailBarChanged();
    getCocktailBarPackage();
  }

  @Override // com.android.server.am.mars.filter.IFilter
  public void deInit() {
    unregisterEnabledCocktailBarChanged();
  }

  @Override // com.android.server.am.mars.filter.IFilter
  public int filter(String str, int i, int i2, int i3) {
    ArrayList arrayList = this.mCocktailBarList;
    if (arrayList == null) {
      return 0;
    }
    synchronized (arrayList) {
      Iterator it = this.mCocktailBarList.iterator();
      while (it.hasNext()) {
        if (((String) it.next()).startsWith(str)
            && (i3 == 4 || MARsPolicyManager.getInstance().getScreenOnState())) {
          return 17;
        }
      }
      return 0;
    }
  }

  public final void getCocktailBarPackage() {
    String stringForUser =
        Settings.System.getStringForUser(
            this.mContext.getContentResolver(),
            "cocktail_bar_enabled_cocktails",
            this.mContext.getUserId());
    if (stringForUser == null) {
      return;
    }
    synchronized (this.mCocktailBarList) {
      this.mCocktailBarList.clear();
      if (stringForUser.contains(KnoxVpnFirewallHelper.DELIMITER)) {
        Collections.addAll(
            this.mCocktailBarList, stringForUser.split(KnoxVpnFirewallHelper.DELIMITER));
      } else {
        this.mCocktailBarList.add(stringForUser);
      }
    }
  }

  public final void registerEnabledCocktailBarChanged() {
    if (this.mRegisteredCocktailBarSettingsObserver) {
      return;
    }
    this.mCocktailBarSettingsObserver =
        new ContentObserver(
            new Handler()) { // from class: com.android.server.am.mars.filter.filter.EdgeAppFilter.1
          @Override // android.database.ContentObserver
          public void onChange(boolean z, Uri uri) {
            EdgeAppFilter.this.getCocktailBarPackage();
          }
        };
    this.mContext
        .getContentResolver()
        .registerContentObserver(
            Settings.System.getUriFor("cocktail_bar_enabled_cocktails"),
            false,
            this.mCocktailBarSettingsObserver,
            this.mContext.getUserId());
    this.mRegisteredCocktailBarSettingsObserver = true;
  }

  public final void unregisterEnabledCocktailBarChanged() {
    try {
      if (this.mRegisteredCocktailBarSettingsObserver) {
        this.mContext
            .getContentResolver()
            .unregisterContentObserver(this.mCocktailBarSettingsObserver);
        this.mRegisteredCocktailBarSettingsObserver = false;
      }
    } catch (IllegalArgumentException unused) {
      Slog.e(
          this.TAG, "IllegalArgumentException occurred in unregisterEnabledCocktailBarChanged()");
    }
  }
}
