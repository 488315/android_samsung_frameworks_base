package com.android.server.am.mars.filter.filter;

import android.content.Context;
import android.util.ArrayMap;
import com.android.server.am.MARsPolicyManager;
import com.android.server.am.mars.filter.IFilter;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class LatestProtectedPackageFilter implements IFilter {
  public static String TAG = "MARs:" + LatestProtectedPackageFilter.class.getSimpleName();
  public Context mContext;
  public ArrayMap mLatestProtectedPackages;
  public int mProtectedAppSizeForGame;

  public abstract class LatestProtectedPackageFilterHolder {
    public static final LatestProtectedPackageFilter INSTANCE = new LatestProtectedPackageFilter();
  }

  @Override // com.android.server.am.mars.filter.IFilter
  public void deInit() {}

  public LatestProtectedPackageFilter() {
    this.mProtectedAppSizeForGame = 0;
    this.mContext = null;
    this.mLatestProtectedPackages = new ArrayMap();
  }

  public static LatestProtectedPackageFilter getInstance() {
    return LatestProtectedPackageFilterHolder.INSTANCE;
  }

  public final void setContext(Context context) {
    this.mContext = context;
  }

  @Override // com.android.server.am.mars.filter.IFilter
  public void init(Context context) {
    setContext(context);
  }

  /* JADX WARN: Code restructure failed: missing block: B:40:0x0045, code lost:

     if (r3 > 2) goto L26;
  */
  /* JADX WARN: Removed duplicated region for block: B:26:0x0052 A[Catch: all -> 0x006f, TryCatch #0 {, blocks: (B:13:0x001a, B:15:0x0028, B:17:0x002e, B:26:0x0052, B:30:0x005e, B:34:0x0068, B:32:0x006a, B:39:0x0047, B:41:0x0049, B:43:0x004d, B:44:0x006d), top: B:12:0x001a }] */
  /* JADX WARN: Removed duplicated region for block: B:30:0x005e A[Catch: all -> 0x006f, TryCatch #0 {, blocks: (B:13:0x001a, B:15:0x0028, B:17:0x002e, B:26:0x0052, B:30:0x005e, B:34:0x0068, B:32:0x006a, B:39:0x0047, B:41:0x0049, B:43:0x004d, B:44:0x006d), top: B:12:0x001a }] */
  @Override // com.android.server.am.mars.filter.IFilter
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public int filter(String str, int i, int i2, int i3) {
    int i4;
    if (str == null) {
      return 0;
    }
    if (MARsPolicyManager.getInstance().checkIsChinaModel() && (i3 == 11 || i3 == 15)) {
      return 0;
    }
    synchronized (this.mLatestProtectedPackages) {
      ArrayList arrayList = (ArrayList) this.mLatestProtectedPackages.get(Integer.valueOf(i));
      if (arrayList != null && arrayList.size() > 0) {
        int size = arrayList.size();
        if (i3 != 9) {
          if (i3 != 11) {
            if (i3 != 15) {
              if (i3 != 18) {}
            } else if (size > 2) {
              i4 = size - 2;
            }
            i4 = 0;
          }
          if (i3 == 15 || MARsPolicyManager.getInstance().getScreenOnState()) {
            for (int i5 = size - 1; i5 >= i4; i5--) {
              if (str.equals(arrayList.get(i5))) {
                return 2;
              }
            }
          }
        }
        int i6 = this.mProtectedAppSizeForGame;
        if (size > i6) {
          i4 = size - i6;
          if (i3 == 15) {}
          while (i5 >= i4) {}
        }
        i4 = 0;
        if (i3 == 15) {}
        while (i5 >= i4) {}
      }
      return 0;
    }
  }

  public void setLatestProtectedPkg(String str, int i) {
    ArrayMap arrayMap = this.mLatestProtectedPackages;
    if (arrayMap != null) {
      synchronized (arrayMap) {
        ArrayList arrayList = (ArrayList) this.mLatestProtectedPackages.get(Integer.valueOf(i));
        if (arrayList != null) {
          if (arrayList.contains(str)) {
            arrayList.remove(str);
            arrayList.add(str);
          } else if (arrayList.size() == 6) {
            arrayList.remove(0);
            arrayList.add(str);
          } else {
            arrayList.add(str);
          }
        } else {
          arrayList = new ArrayList();
          arrayList.add(str);
        }
        this.mLatestProtectedPackages.put(Integer.valueOf(i), arrayList);
      }
    }
  }

  public void setProtectAppCntForGame(int i) {
    this.mProtectedAppSizeForGame = i;
  }
}
