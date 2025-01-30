package com.android.server.companion.datatransfer.contextsync;

import android.os.Bundle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/* loaded from: classes.dex */
public class CallMetadataSyncData {
  public final Map mCalls = new HashMap();
  public final List mCallCreateRequests = new ArrayList();
  public final List mCallControlRequests = new ArrayList();
  public final List mCallFacilitators = new ArrayList();

  public void addCall(Call call) {
    this.mCalls.put(call.getId(), call);
  }

  public boolean hasCall(String str) {
    return this.mCalls.containsKey(str);
  }

  public Collection getCalls() {
    return this.mCalls.values();
  }

  public void addCallCreateRequest(CallCreateRequest callCreateRequest) {
    this.mCallCreateRequests.add(callCreateRequest);
  }

  public List getCallCreateRequests() {
    return this.mCallCreateRequests;
  }

  public void addCallControlRequest(CallControlRequest callControlRequest) {
    this.mCallControlRequests.add(callControlRequest);
  }

  public List getCallControlRequests() {
    return this.mCallControlRequests;
  }

  public void addFacilitator(CallFacilitator callFacilitator) {
    this.mCallFacilitators.add(callFacilitator);
  }

  public List getFacilitators() {
    return this.mCallFacilitators;
  }

  public class CallFacilitator {
    public String mExtendedIdentifier;
    public String mIdentifier;
    public boolean mIsTel;
    public String mName;

    public CallFacilitator() {}

    public CallFacilitator(String str, String str2, String str3) {
      this.mName = str;
      this.mIdentifier = str2;
      this.mExtendedIdentifier = str3;
    }

    public String getName() {
      return this.mName;
    }

    public String getIdentifier() {
      return this.mIdentifier;
    }

    public String getExtendedIdentifier() {
      return this.mExtendedIdentifier;
    }

    public boolean isTel() {
      return this.mIsTel;
    }

    public void setName(String str) {
      this.mName = str;
    }

    public void setIdentifier(String str) {
      this.mIdentifier = str;
    }

    public void setExtendedIdentifier(String str) {
      this.mExtendedIdentifier = str;
    }

    public void setIsTel(boolean z) {
      this.mIsTel = z;
    }
  }

  public class CallControlRequest {
    public int mControl;
    public String mId;

    public void setId(String str) {
      this.mId = str;
    }

    public void setControl(int i) {
      this.mControl = i;
    }

    public String getId() {
      return this.mId;
    }

    public int getControl() {
      return this.mControl;
    }
  }

  public class CallCreateRequest {
    public String mAddress;
    public CallFacilitator mFacilitator;
    public String mId;

    public void setId(String str) {
      this.mId = str;
    }

    public void setAddress(String str) {
      this.mAddress = str;
    }

    public void setFacilitator(CallFacilitator callFacilitator) {
      this.mFacilitator = callFacilitator;
    }

    public String getId() {
      return this.mId;
    }

    public String getAddress() {
      return this.mAddress;
    }

    public CallFacilitator getFacilitator() {
      return this.mFacilitator;
    }
  }

  public class Call {
    public byte[] mAppIcon;
    public String mCallerId;
    public final Set mControls = new HashSet();
    public int mDirection;
    public CallFacilitator mFacilitator;
    public String mId;
    public int mStatus;

    public static Call fromBundle(Bundle bundle) {
      Call call = new Call();
      if (bundle != null) {
        call.setId(
            bundle.getString("com.android.companion.datatransfer.contextsync.extra.CALL_ID"));
        call.setCallerId(
            bundle.getString(
                "com.android.server.companion.datatransfer.contextsync.extra.CALLER_ID"));
        call.setAppIcon(
            bundle.getByteArray(
                "com.android.server.companion.datatransfer.contextsync.extra.APP_ICON"));
        call.setFacilitator(
            new CallFacilitator(
                bundle.getString(
                    "com.android.server.companion.datatransfer.contextsync.extra.FACILITATOR_NAME"),
                bundle.getString(
                    "com.android.server.companion.datatransfer.contextsync.extra.FACILITATOR_ID"),
                bundle.getString(
                    "com.android.server.companion.datatransfer.contextsync.extra.FACILITATOR_EXT_ID")));
        call.setStatus(
            bundle.getInt("com.android.server.companion.datatransfer.contextsync.extra.STATUS"));
        call.setDirection(
            bundle.getInt("com.android.server.companion.datatransfer.contextsync.extra.DIRECTION"));
        call.setControls(
            new HashSet(
                bundle.getIntegerArrayList(
                    "com.android.server.companion.datatransfer.contextsync.extra.CONTROLS")));
      }
      return call;
    }

    public Bundle writeToBundle() {
      Bundle bundle = new Bundle();
      bundle.putString("com.android.companion.datatransfer.contextsync.extra.CALL_ID", this.mId);
      bundle.putString(
          "com.android.server.companion.datatransfer.contextsync.extra.CALLER_ID", this.mCallerId);
      bundle.putByteArray(
          "com.android.server.companion.datatransfer.contextsync.extra.APP_ICON", this.mAppIcon);
      bundle.putString(
          "com.android.server.companion.datatransfer.contextsync.extra.FACILITATOR_NAME",
          this.mFacilitator.getName());
      bundle.putString(
          "com.android.server.companion.datatransfer.contextsync.extra.FACILITATOR_ID",
          this.mFacilitator.getIdentifier());
      bundle.putString(
          "com.android.server.companion.datatransfer.contextsync.extra.FACILITATOR_EXT_ID",
          this.mFacilitator.getExtendedIdentifier());
      bundle.putInt(
          "com.android.server.companion.datatransfer.contextsync.extra.STATUS", this.mStatus);
      bundle.putInt(
          "com.android.server.companion.datatransfer.contextsync.extra.DIRECTION", this.mDirection);
      bundle.putIntegerArrayList(
          "com.android.server.companion.datatransfer.contextsync.extra.CONTROLS",
          new ArrayList<>(this.mControls));
      return bundle;
    }

    public void setId(String str) {
      this.mId = str;
    }

    public void setCallerId(String str) {
      this.mCallerId = str;
    }

    public void setAppIcon(byte[] bArr) {
      this.mAppIcon = bArr;
    }

    public void setFacilitator(CallFacilitator callFacilitator) {
      this.mFacilitator = callFacilitator;
    }

    public void setStatus(int i) {
      this.mStatus = i;
    }

    public void setDirection(int i) {
      this.mDirection = i;
    }

    public void addControl(int i) {
      this.mControls.add(Integer.valueOf(i));
    }

    public void setControls(Set set) {
      this.mControls.clear();
      this.mControls.addAll(set);
    }

    public String getId() {
      return this.mId;
    }

    public String getCallerId() {
      return this.mCallerId;
    }

    public byte[] getAppIcon() {
      return this.mAppIcon;
    }

    public CallFacilitator getFacilitator() {
      return this.mFacilitator;
    }

    public int getStatus() {
      return this.mStatus;
    }

    public int getDirection() {
      return this.mDirection;
    }

    public Set getControls() {
      return this.mControls;
    }

    public boolean hasControl(int i) {
      return this.mControls.contains(Integer.valueOf(i));
    }

    public boolean equals(Object obj) {
      String str;
      return (obj instanceof Call) && (str = this.mId) != null && str.equals(((Call) obj).getId());
    }

    public int hashCode() {
      return Objects.hashCode(this.mId);
    }
  }
}
