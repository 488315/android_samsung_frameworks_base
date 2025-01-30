package com.android.server.cocktailbar.settings;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Binder;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Slog;
import android.util.SparseArray;
import com.android.server.cocktailbar.utils.CocktailBarConfig;
import com.samsung.android.cocktailbar.Cocktail;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/* loaded from: classes.dex */
public class CocktailBarSettings {
  public static final String TAG = "CocktailBarSettings";
  public final Context mContext;
  public int mCurrentUserId;
  public String mEnabledCocktailsStrCache;
  public final ContentResolver mResolver;
  public final TextUtils.SimpleStringSplitter mEnabledCocktailsSplitter =
      new TextUtils.SimpleStringSplitter(';');
  public HashMap mCocktailMap = new HashMap();
  public ArrayList mEnabledCocktailListCache = new ArrayList();
  public Handler mHandler = new Handler(Looper.getMainLooper());

  public class CocktailInfo {
    public final int cocktailId;
    public final String packageName;

    public CocktailInfo(int i, String str) {
      this.cocktailId = i;
      this.packageName = str;
    }
  }

  public CocktailBarSettings(Context context, SparseArray sparseArray, int i) {
    this.mContext = context;
    this.mResolver = context.getContentResolver();
    this.mCurrentUserId = i;
    initCocktailMap(sparseArray);
    updateEnabledCocktailList();
  }

  public final void initCocktailMap(SparseArray sparseArray) {
    synchronized (this.mCocktailMap) {
      this.mCocktailMap.clear();
      int size = sparseArray.size();
      for (int i = 0; i < size; i++) {
        Cocktail cocktail = (Cocktail) sparseArray.valueAt(i);
        ComponentName provider = cocktail.getProvider();
        if (provider != null) {
          this.mCocktailMap.put(
              cocktail.getProvider().getClassName(),
              new CocktailInfo(cocktail.getCocktailId(), provider.getPackageName()));
        }
      }
    }
  }

  public void updateInstalledCocktails(SparseArray sparseArray) {
    initCocktailMap(sparseArray);
    updateEnabledCocktailList();
  }

  public void setEnabledCocktailsLocked(ArrayList arrayList) {
    int size = arrayList.size();
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < size; i++) {
      if (i > 0) {
        sb.append(';');
      }
      sb.append((String) arrayList.get(i));
    }
    try {
      putEnabledCocktailsStr(sb.toString());
      if (TextUtils.isEmpty(sb.toString())) {
        Settings.System.putStringForUser(
            this.mResolver, "previous_enable_list", getEnabledCocktailsStr(), this.mCurrentUserId);
        ArrayList enableCocktailIds = getEnableCocktailIds();
        if (enableCocktailIds != null) {
          Settings.System.putInt(
              this.mResolver, "previous_enable_id_cnt", enableCocktailIds.size());
        }
      }
    } catch (Exception e) {
      Slog.d(TAG, "setEnabledCocktailsLocked: " + e.toString());
    }
  }

  public final void putEnabledCocktailsStr(String str) {
    Settings.System.putStringForUser(
        this.mResolver, "cocktail_bar_enabled_cocktails", str, this.mCurrentUserId);
    this.mEnabledCocktailsStrCache = str;
    Slog.d(TAG, "putEnabledCocktailsStr: " + str);
  }

  public String getEnabledCocktailsStr() {
    this.mEnabledCocktailsStrCache =
        Settings.System.getStringForUser(
            this.mResolver, "cocktail_bar_enabled_cocktails", this.mCurrentUserId);
    Slog.d(
        TAG,
        "getEnabledCocktailsStr: " + this.mEnabledCocktailsStrCache + ", " + this.mCurrentUserId);
    return this.mEnabledCocktailsStrCache;
  }

  public ArrayList getEnableCocktailIds() {
    ArrayList arrayList = new ArrayList();
    Iterator it = this.mEnabledCocktailListCache.iterator();
    while (it.hasNext()) {
      CocktailInfo cocktailInfo = (CocktailInfo) this.mCocktailMap.get((String) it.next());
      if (cocktailInfo != null) {
        arrayList.add(Integer.valueOf(cocktailInfo.cocktailId));
      }
    }
    return arrayList;
  }

  public final void updateEnabledCocktailList() {
    if (isLocalBinder()) {
      synchronized (this.mCocktailMap) {
        updateEnabledCocktailListLocked();
      }
      return;
    }
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.cocktailbar.settings.CocktailBarSettings.1
          @Override // java.lang.Runnable
          public void run() {
            synchronized (CocktailBarSettings.this.mCocktailMap) {
              CocktailBarSettings.this.updateEnabledCocktailListLocked();
            }
          }
        });
  }

  public final boolean updateEnabledCocktailListLocked() {
    String enabledCocktailsStr = getEnabledCocktailsStr();
    ArrayList arrayList = new ArrayList();
    if (TextUtils.isEmpty(enabledCocktailsStr)) {
      return false;
    }
    this.mEnabledCocktailsSplitter.setString(enabledCocktailsStr);
    boolean z = false;
    while (this.mEnabledCocktailsSplitter.hasNext()) {
      String next = this.mEnabledCocktailsSplitter.next();
      if (((CocktailInfo) this.mCocktailMap.get(next)) != null) {
        arrayList.add(next);
      } else {
        String convertedComponent =
            CocktailBarConfig.getInstance(this.mContext).getConvertedComponent(next);
        CocktailInfo cocktailInfo = (CocktailInfo) this.mCocktailMap.get(convertedComponent);
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("updateEnabledCocktailListLocked chagned old = ");
        sb.append(next);
        sb.append(",new=");
        sb.append(convertedComponent);
        sb.append(",info=");
        sb.append(cocktailInfo != null);
        Slog.d(str, sb.toString());
        if (cocktailInfo != null) {
          arrayList.add(convertedComponent);
        }
        z = true;
      }
    }
    if (z) {
      setEnabledCocktailsLocked(arrayList);
    }
    this.mEnabledCocktailListCache = arrayList;
    return true;
  }

  public boolean isEnabledCocktail(int i) {
    synchronized (this.mCocktailMap) {
      Iterator it = this.mEnabledCocktailListCache.iterator();
      StringBuffer stringBuffer = null;
      while (it.hasNext()) {
        String str = (String) it.next();
        CocktailInfo cocktailInfo = (CocktailInfo) this.mCocktailMap.get(str);
        if (cocktailInfo == null) {
          if (stringBuffer == null) {
            stringBuffer = new StringBuffer("isEnabledCocktail: invalid ");
            stringBuffer.append(" for cid=");
            stringBuffer.append(i);
            stringBuffer.append(" uid=");
            stringBuffer.append(this.mCurrentUserId);
          }
          stringBuffer.append(" [");
          stringBuffer.append(str);
          stringBuffer.append("]");
        } else if (cocktailInfo.cocktailId == i) {
          return true;
        }
      }
      if (stringBuffer != null) {
        Slog.d(TAG, stringBuffer.toString());
      }
      return false;
    }
  }

  public boolean isEnabledCocktail(String str) {
    synchronized (this.mCocktailMap) {
      Iterator it = this.mEnabledCocktailListCache.iterator();
      StringBuffer stringBuffer = null;
      while (it.hasNext()) {
        String str2 = (String) it.next();
        CocktailInfo cocktailInfo = (CocktailInfo) this.mCocktailMap.get(str2);
        if (cocktailInfo == null) {
          if (stringBuffer == null) {
            stringBuffer = new StringBuffer("isEnabledCocktail: invalid ");
            stringBuffer.append(" for pName=");
            stringBuffer.append(str);
            stringBuffer.append(" uid=");
            stringBuffer.append(this.mCurrentUserId);
          }
          stringBuffer.append(" [");
          stringBuffer.append(str2);
          stringBuffer.append("]");
        } else if (cocktailInfo.packageName.equals(str)) {
          return true;
        }
      }
      if (stringBuffer != null) {
        Slog.d(TAG, stringBuffer.toString());
      }
      return false;
    }
  }

  public void getChangedCocktailIdsListLocked(ArrayList arrayList, ArrayList arrayList2) {
    ArrayList arrayList3 = this.mEnabledCocktailListCache;
    ArrayList arrayList4 = new ArrayList();
    String enabledCocktailsStr = getEnabledCocktailsStr();
    this.mEnabledCocktailsStrCache = enabledCocktailsStr;
    if (!TextUtils.isEmpty(enabledCocktailsStr)) {
      this.mEnabledCocktailsSplitter.setString(this.mEnabledCocktailsStrCache);
      while (this.mEnabledCocktailsSplitter.hasNext()) {
        String next = this.mEnabledCocktailsSplitter.next();
        CocktailInfo cocktailInfo = (CocktailInfo) this.mCocktailMap.get(next);
        if (cocktailInfo != null) {
          arrayList4.add(next);
          if (!arrayList3.contains(next)) {
            arrayList.add(Integer.valueOf(cocktailInfo.cocktailId));
          }
        }
      }
    }
    Iterator it = arrayList3.iterator();
    while (it.hasNext()) {
      String str = (String) it.next();
      CocktailInfo cocktailInfo2 = (CocktailInfo) this.mCocktailMap.get(str);
      if (cocktailInfo2 != null && !arrayList4.contains(str)) {
        arrayList2.add(Integer.valueOf(cocktailInfo2.cocktailId));
      }
    }
    this.mEnabledCocktailListCache = arrayList4;
  }

  public static boolean isLocalBinder() {
    return Process.myPid() == Binder.getCallingPid();
  }
}
