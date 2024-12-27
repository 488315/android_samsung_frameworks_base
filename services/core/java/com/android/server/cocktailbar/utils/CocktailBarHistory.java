package com.android.server.cocktailbar.utils;

import android.icu.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class CocktailBarHistory {
    public static CocktailBarHistory mInstance;
    public final ArrayList mProcessHistory = new ArrayList();
    public final ArrayList mCocktailBarManagerCommandHistory = new ArrayList();
    public final ArrayList mSemCocktailProviderBrHistory = new ArrayList();
    public final HashMap mPanelUpdateHistory = new HashMap();
    public final HashMap mPowerWhitelistHistory = new HashMap();
    public final Object mLock = new Object();

    public static synchronized CocktailBarHistory getInstance() {
        CocktailBarHistory cocktailBarHistory;
        synchronized (CocktailBarHistory.class) {
            cocktailBarHistory = mInstance;
            if (cocktailBarHistory == null) {
                cocktailBarHistory = new CocktailBarHistory();
                mInstance = cocktailBarHistory;
            }
        }
        return cocktailBarHistory;
    }

    public final void recordCocktailBarManagerCommand(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            stringBuffer.append(new SimpleDateFormat("MM-dd HH:mm:ss.SSS").format(new Date()));
        } catch (Exception e) {
            e.printStackTrace();
            stringBuffer.append(System.currentTimeMillis());
        }
        stringBuffer.append(": ");
        stringBuffer.append(str);
        synchronized (this.mLock) {
            try {
                this.mCocktailBarManagerCommandHistory.add(stringBuffer.toString());
                while (this.mCocktailBarManagerCommandHistory.size() > 40) {
                    this.mCocktailBarManagerCommandHistory.remove(0);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void recordPanelUpdateHistory(int i, String str) {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            stringBuffer.append(new SimpleDateFormat("MM-dd HH:mm:ss.SSS").format(new Date()));
        } catch (Exception e) {
            e.printStackTrace();
            stringBuffer.append(System.currentTimeMillis());
        }
        stringBuffer.append(": ");
        stringBuffer.append(str);
        synchronized (this.mLock) {
            try {
                this.mPanelUpdateHistory.put(Integer.valueOf(i), stringBuffer.toString());
                while (this.mPanelUpdateHistory.size() > 40) {
                    Iterator it = this.mPanelUpdateHistory.entrySet().iterator();
                    if (it.hasNext()) {
                        it.next();
                        it.remove();
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void recordPowerWhitelistHistory(int i, String str) {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            stringBuffer.append(new SimpleDateFormat("MM-dd HH:mm:ss.SSS").format(new Date()));
        } catch (Exception e) {
            e.printStackTrace();
            stringBuffer.append(System.currentTimeMillis());
        }
        stringBuffer.append(": ");
        stringBuffer.append(str);
        synchronized (this.mLock) {
            try {
                this.mPowerWhitelistHistory.put(Integer.valueOf(i), stringBuffer.toString());
                while (this.mPowerWhitelistHistory.size() > 40) {
                    Iterator it = this.mPowerWhitelistHistory.entrySet().iterator();
                    if (it.hasNext()) {
                        it.next();
                        it.remove();
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void recordSemCocktailProviderBr(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            stringBuffer.append(new SimpleDateFormat("MM-dd HH:mm:ss.SSS").format(new Date()));
        } catch (Exception e) {
            e.printStackTrace();
            stringBuffer.append(System.currentTimeMillis());
        }
        stringBuffer.append(": ");
        stringBuffer.append(str);
        synchronized (this.mLock) {
            try {
                this.mSemCocktailProviderBrHistory.add(stringBuffer.toString());
                while (this.mSemCocktailProviderBrHistory.size() > 40) {
                    this.mSemCocktailProviderBrHistory.remove(0);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void recordServiceProcess(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            stringBuffer.append(new SimpleDateFormat("MM-dd HH:mm:ss.SSS").format(new Date()));
        } catch (Exception e) {
            e.printStackTrace();
            stringBuffer.append(System.currentTimeMillis());
        }
        stringBuffer.append(": ");
        stringBuffer.append(str);
        synchronized (this.mLock) {
            try {
                this.mProcessHistory.add(stringBuffer.toString());
                while (this.mProcessHistory.size() > 40) {
                    this.mProcessHistory.remove(0);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        synchronized (this.mLock) {
            try {
                stringBuffer.append("-CocktailBar History\n");
                stringBuffer.append("  [Process History] :\n");
                Iterator it = this.mProcessHistory.iterator();
                while (it.hasNext()) {
                    String str = (String) it.next();
                    if (str != null) {
                        stringBuffer.append("   " + str + "\n");
                    }
                }
                stringBuffer.append("\n");
                stringBuffer.append("  [CocktailBarManager Command History] :\n");
                Iterator it2 = this.mCocktailBarManagerCommandHistory.iterator();
                while (it2.hasNext()) {
                    String str2 = (String) it2.next();
                    if (str2 != null) {
                        stringBuffer.append("   " + str2 + "\n");
                    }
                }
                stringBuffer.append("\n");
                stringBuffer.append("  [SemCocktailProvider BR History] :\n");
                Iterator it3 = this.mSemCocktailProviderBrHistory.iterator();
                while (it3.hasNext()) {
                    String str3 = (String) it3.next();
                    if (str3 != null) {
                        stringBuffer.append("   " + str3 + "\n");
                    }
                }
                stringBuffer.append("\n");
                stringBuffer.append("  [Panel update history] :\n");
                for (Map.Entry entry : this.mPanelUpdateHistory.entrySet()) {
                    stringBuffer.append("   Cocktail id : " + entry.getKey() + " - " + ((String) entry.getValue()) + "\n");
                }
                stringBuffer.append("\n");
                stringBuffer.append("  [Power whitelist history] :\n");
                for (Map.Entry entry2 : this.mPowerWhitelistHistory.entrySet()) {
                    stringBuffer.append("   Cocktail id : " + entry2.getKey() + " - " + ((String) entry2.getValue()) + "\n");
                }
                stringBuffer.append("\n");
            } catch (Throwable th) {
                throw th;
            }
        }
        return stringBuffer.toString();
    }
}
