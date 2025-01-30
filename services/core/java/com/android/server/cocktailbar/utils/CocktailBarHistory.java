package com.android.server.cocktailbar.utils;

import android.icu.text.SimpleDateFormat;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes.dex */
public class CocktailBarHistory {
  public static CocktailBarHistory mInstance;
  public ArrayList mProcessHistory = new ArrayList();
  public ArrayList mCocktailBarManagerCommandHistory = new ArrayList();
  public ArrayList mSemCocktailProviderBrHistory = new ArrayList();
  public HashMap mPanelUpdateHistory = new HashMap();
  public HashMap mPowerWhitelistHistory = new HashMap();
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

  public void recordServiceProcess(String str) {
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
      this.mProcessHistory.add(stringBuffer.toString());
      while (this.mProcessHistory.size() > 40) {
        this.mProcessHistory.remove(0);
      }
    }
  }

  public void recordCocktailBarManagerCommand(String str) {
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
      this.mCocktailBarManagerCommandHistory.add(stringBuffer.toString());
      while (this.mCocktailBarManagerCommandHistory.size() > 40) {
        this.mCocktailBarManagerCommandHistory.remove(0);
      }
    }
  }

  public void recordSemCocktailProviderBr(String str) {
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
      this.mSemCocktailProviderBrHistory.add(stringBuffer.toString());
      while (this.mSemCocktailProviderBrHistory.size() > 40) {
        this.mSemCocktailProviderBrHistory.remove(0);
      }
    }
  }

  public void recordPanelUpdateHistory(int i, String str) {
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
      this.mPanelUpdateHistory.put(Integer.valueOf(i), stringBuffer.toString());
      while (this.mPanelUpdateHistory.size() > 40) {
        Iterator it = this.mPanelUpdateHistory.entrySet().iterator();
        if (it.hasNext()) {
          it.next();
          it.remove();
        }
      }
    }
  }

  public void recordPowerWhitelistHistory(int i, String str) {
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
      this.mPowerWhitelistHistory.put(Integer.valueOf(i), stringBuffer.toString());
      while (this.mPowerWhitelistHistory.size() > 40) {
        Iterator it = this.mPowerWhitelistHistory.entrySet().iterator();
        if (it.hasNext()) {
          it.next();
          it.remove();
        }
      }
    }
  }

  public String toString() {
    StringBuffer stringBuffer = new StringBuffer();
    synchronized (this.mLock) {
      stringBuffer.append("-CocktailBar History\n");
      stringBuffer.append("  [Process History] :\n");
      Iterator it = this.mProcessHistory.iterator();
      while (it.hasNext()) {
        String str = (String) it.next();
        if (str != null) {
          stringBuffer.append("   " + str + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        }
      }
      stringBuffer.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
      stringBuffer.append("  [CocktailBarManager Command History] :\n");
      Iterator it2 = this.mCocktailBarManagerCommandHistory.iterator();
      while (it2.hasNext()) {
        String str2 = (String) it2.next();
        if (str2 != null) {
          stringBuffer.append("   " + str2 + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        }
      }
      stringBuffer.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
      stringBuffer.append("  [SemCocktailProvider BR History] :\n");
      Iterator it3 = this.mSemCocktailProviderBrHistory.iterator();
      while (it3.hasNext()) {
        String str3 = (String) it3.next();
        if (str3 != null) {
          stringBuffer.append("   " + str3 + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        }
      }
      stringBuffer.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
      stringBuffer.append("  [Panel update history] :\n");
      for (Map.Entry entry : this.mPanelUpdateHistory.entrySet()) {
        stringBuffer.append(
            "   Cocktail id : "
                + entry.getKey()
                + " - "
                + ((String) entry.getValue())
                + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
      }
      stringBuffer.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
      stringBuffer.append("  [Power whitelist history] :\n");
      for (Map.Entry entry2 : this.mPowerWhitelistHistory.entrySet()) {
        stringBuffer.append(
            "   Cocktail id : "
                + entry2.getKey()
                + " - "
                + ((String) entry2.getValue())
                + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
      }
      stringBuffer.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
    }
    return stringBuffer.toString();
  }
}
