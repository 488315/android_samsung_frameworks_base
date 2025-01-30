package com.android.server.enterprise.common;

import android.content.Context;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import com.android.server.ServiceKeeper;
import com.android.server.enterprise.adapter.AdapterRegistry;
import com.android.server.enterprise.adapter.IPersonaManagerAdapter;
import java.util.List;

/* loaded from: classes2.dex */
public final class EnterprisePermissionChecker {
  public static EnterprisePermissionChecker sInstance;
  public Context mContext;
  public IPersonaManagerAdapter mPersonaManagerAdapter;

  public EnterprisePermissionChecker(Context context) {
    this.mContext = context;
  }

  public final IPersonaManagerAdapter getPersonaManagerAdapter() {
    if (this.mPersonaManagerAdapter == null) {
      this.mPersonaManagerAdapter =
          (IPersonaManagerAdapter) AdapterRegistry.getAdapter(IPersonaManagerAdapter.class);
    }
    return this.mPersonaManagerAdapter;
  }

  public static EnterprisePermissionChecker getInstance(Context context) {
    if (sInstance == null) {
      sInstance = new EnterprisePermissionChecker(context);
    }
    return sInstance;
  }

  public void enforceCallingOrSelfPermissions(List list, String str) {
    if (list == null || list.isEmpty()) {
      Log.e("EnterprisePermissionChecker", "no permission provided");
      return;
    }
    try {
      this.mContext.enforceCallingOrSelfPermission((String) list.get(0), str);
    } catch (SecurityException e) {
      if (list.size() == 1) {
        throw e;
      }
      try {
        this.mContext.enforceCallingOrSelfPermission((String) list.get(1), str);
      } catch (SecurityException e2) {
        String message = e2.getMessage();
        if (message != null) {
          message = message + "," + ((String) list.get(0));
        }
        throw new SecurityException(message);
      }
    }
  }

  public void enforceAuthorization(String str, String str2) {
    Bundle knoxInfo = getPersonaManagerAdapter().getKnoxInfo();
    if (knoxInfo == null || !"2.0".equals(knoxInfo.getString("version"))) {
      return;
    }
    int callingUid = Binder.getCallingUid();
    int callingPid = Binder.getCallingPid();
    if (!ServiceKeeper.isTableActive()) {
      ServiceKeeper.authorizeLoadProcedure();
    }
    if (ServiceKeeper.isAuthorized(this.mContext, callingPid, callingUid, str, str2) == 0) {
      return;
    }
    throw new SecurityException(
        "Method " + str2 + " from service " + str + " is not authorized to be called!!!");
  }
}
