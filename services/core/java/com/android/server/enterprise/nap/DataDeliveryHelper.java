package com.android.server.enterprise.nap;

import android.os.Process;
import android.os.UserHandle;
import android.p005os.IInstalld;
import android.util.Log;
import com.samsung.android.knox.net.nap.serviceprovider.INetworkAnalyticsService;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class DataDeliveryHelper {
  public static final boolean DBG = NetworkAnalyticsService.DBG;
  public String identifier;
  public int operationUserId;
  public NetworkAnalyticsConfigStore.NAPConfigProfile profile;
  public NetworkAnalyticsService.NetworkAnalyticsServiceConnection serviceConnection;

  public DataDeliveryHelper(
      NetworkAnalyticsConfigStore.NAPConfigProfile nAPConfigProfile,
      NetworkAnalyticsService.NetworkAnalyticsServiceConnection networkAnalyticsServiceConnection,
      int i) {
    this.profile = nAPConfigProfile;
    this.serviceConnection = networkAnalyticsServiceConnection;
    this.operationUserId = i;
    if (nAPConfigProfile != null) {
      this.identifier =
          NetworkAnalyticsService.getTransformedVendorName(nAPConfigProfile.getProfileName(), i);
    }
  }

  /* JADX WARN: Multi-variable type inference failed */
  /* JADX WARN: Type inference failed for: r0v66 */
  /* JADX WARN: Type inference failed for: r0v75 */
  /* JADX WARN: Type inference failed for: r0v76 */
  public String processData(String str) {
    String str2;
    String str3;
    String str4;
    String str5;
    String str6;
    String str7;
    String str8;
    String str9;
    String str10;
    String str11;
    String str12;
    try {
      try {
        try {
          JSONObject jSONObject = new JSONObject(str);
          int flags = this.profile.getFlags();
          JSONObject jSONObject2 = new JSONObject();
          String str13 = null;
          try {
            String optString = jSONObject.optString("uid", null);
            if (optString != null) {
              try {
                int userId = UserHandle.getUserId(Integer.parseInt(optString));
                if (Integer.parseInt(optString) == 0) {
                  str4 = "start";
                  int blockDnsFlow = blockDnsFlow(jSONObject, this.operationUserId, userId);
                  str5 = "bsent";
                  if (blockDnsFlow > 0) {
                    if (blockDnsFlow == 3) {
                      return null;
                    }
                    str6 = null;
                    try {
                      try {
                        if (userId != this.operationUserId) {
                          return null;
                        }
                      } catch (NumberFormatException unused) {
                        return null;
                      }
                    } catch (JSONException e) {
                      e = e;
                      str2 = str6;
                      str3 = "NetworkAnalytics:DataDeliveryHelper";
                      Log.e(str3, "processData: JSONException", e);
                      return str2;
                    } catch (Exception e2) {
                      e = e2;
                      str2 = str6;
                      Log.e("NetworkAnalytics:DataDeliveryHelper", "processData: Exception", e);
                      return str2;
                    }
                  }
                } else {
                  str4 = "start";
                  str5 = "bsent";
                  if (userId != this.operationUserId) {
                    return null;
                  }
                }
              } catch (NumberFormatException unused2) {
                return null;
              }
            } else {
              str4 = "start";
              str5 = "bsent";
            }
            jSONObject2.put("recordtype", jSONObject.optString("recordtype", null));
            if ((32768 & flags) != 0 || flags == 0) {
              jSONObject2.put("uid", jSONObject.optString("uid", null));
            }
            if ((flags & 256) != 0 || flags == 0) {
              str6 = null;
              jSONObject2.put("pid", jSONObject.optString("pid", null));
            }
            if ((flags & IInstalld.FLAG_USE_QUOTA) != 0 || flags == 0) {
              str7 = null;
              try {
                jSONObject2.put("puid", jSONObject.optString("puid", null));
              } catch (JSONException e3) {
                e = e3;
                str2 = str7;
                str3 = "NetworkAnalytics:DataDeliveryHelper";
                Log.e(str3, "processData: JSONException", e);
                return str2;
              } catch (Exception e4) {
                e = e4;
                str2 = str7;
                Log.e("NetworkAnalytics:DataDeliveryHelper", "processData: Exception", e);
                return str2;
              }
            }
            if ((flags & IInstalld.FLAG_FORCE) != 0 || flags == 0) {
              jSONObject2.put("src", jSONObject.optString("src", null));
            }
            if ((flags & 8) != 0 || flags == 0) {
              jSONObject2.put("dst", jSONObject.optString("dst", null));
            }
            if ((flags & 16384) != 0 || flags == 0) {
              str7 = null;
              jSONObject2.put("sport", jSONObject.optString("sport", null));
            }
            if ((flags & 16) != 0 || flags == 0) {
              str8 = null;
              try {
                jSONObject2.put("dport", jSONObject.optString("dport", null));
              } catch (JSONException e5) {
                e = e5;
                str2 = str8;
                str3 = "NetworkAnalytics:DataDeliveryHelper";
                Log.e(str3, "processData: JSONException", e);
                return str2;
              } catch (Exception e6) {
                e = e6;
                str2 = str8;
                Log.e("NetworkAnalytics:DataDeliveryHelper", "processData: Exception", e);
                return str2;
              }
            }
            if ((flags & IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES) != 0 || flags == 0) {
              str9 = null;
              jSONObject2.put("protocol", jSONObject.optString("protocol", null));
            } else {
              str9 = null;
            }
            try {
              String optString2 = jSONObject.optString("procname", str9);
              if (optString2 == null) {
                return null;
              }
              try {
                if (optString2.isEmpty()) {
                  return null;
                }
                if ((flags & 1024) != 0 || flags == 0) {
                  jSONObject2.put("procname", optString2);
                }
                if ((flags & 512) != 0 || flags == 0) {
                  try {
                    String optString3 = jSONObject.optString("uid", null);
                    String optString4 = jSONObject.optString("procname", null);
                    String optString5 = jSONObject.optString("pid", null);
                    if (optString3 != null && optString4 != null) {
                      if (!optString3.equals("0")
                          && !optString5.equals(Integer.toString(Process.myPid()))) {
                        jSONObject2.put(
                            "prochash",
                            NetworkAnalyticsDataDelivery.getPackageHash(
                                Integer.parseInt(optString3), optString4));
                      }
                      jSONObject2.put("prochash", (Object) null);
                    }
                  } catch (Exception unused3) {
                    jSONObject2.put("prochash", (Object) null);
                  }
                }
                if ((flags & 128) != 0 || flags == 0) {
                  str8 = null;
                  jSONObject2.put("parentprocname", jSONObject.optString("parentprocname", null));
                }
                if ((262144 & flags) != 0 || flags == 0) {
                  try {
                    String optString6 = jSONObject.optString("puid", null);
                    String optString7 = jSONObject.optString("parentprocname", null);
                    String optString8 = jSONObject.optString("ppid", null);
                    if (optString6 != null && optString7 != null) {
                      if (!optString6.equals("0")
                          && !optString8.equals(Integer.toString(Process.myPid()))) {
                        jSONObject2.put(
                            "parentprochash",
                            NetworkAnalyticsDataDelivery.getPackageHash(
                                Integer.parseInt(optString6), optString7));
                      }
                      jSONObject2.put("parentprochash", (Object) null);
                    }
                  } catch (Exception unused4) {
                    str10 = null;
                    jSONObject2.put("parentprochash", (Object) null);
                  }
                }
                str10 = null;
                if ((flags & 2) != 0 || flags == 0) {
                  String str14 = str5;
                  jSONObject2.put(str14, jSONObject.optString(str14, str10));
                }
                if ((flags & 64) != 0 || flags == 0) {
                  String str15 = str4;
                  jSONObject2.put(str15, jSONObject.optString(str15, null));
                }
                if ((flags & 4) != 0 || flags == 0) {
                  jSONObject2.put("end", jSONObject.optString("end", null));
                }
                if ((flags & 1) != 0 || flags == 0) {
                  jSONObject2.put("brecv", jSONObject.optString("brecv", null));
                }
                if ((flags & 32) != 0 || flags == 0) {
                  jSONObject2.put("hostname", jSONObject.optString("hostname", null));
                }
                if ((65536 & flags) != 0 || flags == 0) {
                  str11 = null;
                  try {
                    String optString9 = jSONObject.optString("dport", null);
                    if (optString9 != null) {
                      str12 = optString9.equals("53");
                      try {
                        if (str12 != 0) {
                          String str16 = "dnsuid";
                          jSONObject2.put(str16, jSONObject.optString(str16, null));
                          str12 = str16;
                        } else {
                          String str17 = "dnsuid";
                          jSONObject2.put(str17, (Object) null);
                          str12 = str17;
                        }
                      } catch (Exception unused5) {
                        jSONObject2.put(str12, (Object) null);
                        if ((131072 & flags) == 0) {}
                        jSONObject2.put("ppid", jSONObject.optString("ppid", str11));
                        if ((524288 & flags) == 0) {}
                        str13 = null;
                        jSONObject2.put("iface", jSONObject.optString("iface", null));
                        return jSONObject2.toString();
                      }
                    }
                  } catch (Exception unused6) {
                    str12 = "dnsuid";
                  }
                } else {
                  str11 = null;
                }
                if ((131072 & flags) == 0 || flags == 0) {
                  jSONObject2.put("ppid", jSONObject.optString("ppid", str11));
                }
                if ((524288 & flags) == 0 || flags == 0) {
                  str13 = null;
                  jSONObject2.put("iface", jSONObject.optString("iface", null));
                }
                return jSONObject2.toString();
              } catch (Exception unused7) {
                return null;
              }
            } catch (Exception unused8) {
              return str9;
            }
          } catch (JSONException e7) {
            e = e7;
            str2 = str13;
          } catch (Exception e8) {
            e = e8;
            str2 = str13;
          }
        } catch (JSONException e9) {
          e = e9;
          str2 = null;
        }
      } catch (JSONException e10) {
        e = e10;
        str3 = "NetworkAnalytics:DataDeliveryHelper";
        str2 = null;
      }
    } catch (Exception e11) {
      e = e11;
      str2 = null;
    }
  }

  public NetworkAnalyticsConfigStore.NAPConfigProfile getProfile() {
    return this.profile;
  }

  public NetworkAnalyticsService.NetworkAnalyticsServiceConnection getServiceConnection() {
    return this.serviceConnection;
  }

  public INetworkAnalyticsService getServiceBinder() {
    NetworkAnalyticsService.NetworkAnalyticsServiceConnection networkAnalyticsServiceConnection =
        this.serviceConnection;
    if (networkAnalyticsServiceConnection != null) {
      return networkAnalyticsServiceConnection.getBinderObject();
    }
    return null;
  }

  public String getIdentifier() {
    return this.identifier;
  }

  public final int blockDnsFlow(JSONObject jSONObject, int i, int i2) {
    try {
      if (!jSONObject.optString("dport", null).equals("53")) {
        return 1;
      }
      int userId = UserHandle.getUserId(Integer.parseInt(jSONObject.optString("dnsuid", null)));
      if (i2 == 0 && i == 0 && userId != 0) {
        return 3;
      }
      return userId != i ? 2 : 0;
    } catch (Exception unused) {
      return 1;
    }
  }
}
