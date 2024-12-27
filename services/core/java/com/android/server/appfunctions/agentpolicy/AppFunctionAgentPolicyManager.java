package com.android.server.appfunctions.agentpolicy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.os.SystemProperties;
import android.util.Base64;
import android.util.IndentingPrintWriter;
import android.util.Slog;

import com.android.server.appfunctions.AppFunctionManagerServiceImpl$$ExternalSyntheticLambda3;

import com.samsung.android.knox.custom.KnoxCustomManagerService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class AppFunctionAgentPolicyManager {
    public final AppFunctionAgentPolicyCipher mAppFunctionAgentPolicyCipher;
    public final Context mContext;
    public final ScheduledExecutorService mExecutorService;
    public final PackageManager mPackageManager;
    public final AnonymousClass1 mScpmPolicyUpdateReceiver;
    public final Runnable mUpdateMetadataRunnable;
    public static final boolean USER_BUILD = "user".equals(Build.TYPE);
    public static final boolean SHIP_BUILD =
            "true".equals(SystemProperties.get("ro.product_ship", "false"));
    public final Uri SCPM_URI_V2 = Uri.parse("content://com.samsung.android.scpm.policy/");
    public final AgentPolicyInfo defaultAgentPolicyInfo = new AgentPolicyInfo();
    public final AgentPolicyInfo updatedAgentPolicyInfo = new AgentPolicyInfo();
    public final AgentPolicyInfo appliedAgentPolicyInfo = new AgentPolicyInfo();
    public final AgentPolicyInfo scpmAgentPolicyInfo = new AgentPolicyInfo();
    public final Object mLock = new Object();
    public String mScpmToken = null;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.appfunctions.agentpolicy.AppFunctionAgentPolicyManager$1, reason: invalid class name */
    public final class AnonymousClass1 extends BroadcastReceiver {
        public AnonymousClass1() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(final Context context, Intent intent) {
            if ("com.samsung.android.scpm.policy.UPDATE.cross-app-action-agent-policy"
                    .equals(intent.getAction())) {
                boolean z = AppFunctionAgentPolicyManager.USER_BUILD;
                Slog.i("AppFunctionAgentPolicyManager", "Cross app action agent policy update");
                AppFunctionAgentPolicyManager.this.mExecutorService.execute(
                        new Runnable() { // from class:
                            // com.android.server.appfunctions.agentpolicy.AppFunctionAgentPolicyManager$1$$ExternalSyntheticLambda0
                            /* JADX WARN: Code restructure failed: missing block: B:15:0x0042, code lost:

                               if (r7 != null) goto L12;
                            */
                            @Override // java.lang.Runnable
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                                To view partially-correct code enable 'Show inconsistent code' option in preferences
                            */
                            public final void run() {
                                /*
                                    r7 = this;
                                    com.android.server.appfunctions.agentpolicy.AppFunctionAgentPolicyManager$1 r0 = com.android.server.appfunctions.agentpolicy.AppFunctionAgentPolicyManager.AnonymousClass1.this
                                    android.content.Context r7 = r2
                                    com.android.server.appfunctions.agentpolicy.AppFunctionAgentPolicyManager r1 = com.android.server.appfunctions.agentpolicy.AppFunctionAgentPolicyManager.this
                                    com.android.server.appfunctions.agentpolicy.AppFunctionAgentPolicyManager$AgentPolicyInfo r2 = r1.scpmAgentPolicyInfo
                                    java.lang.String r3 = "AppFunctionAgentPolicyManager"
                                    java.lang.String r4 = "loadScpmAgentPolicy start"
                                    android.util.Slog.d(r3, r4)
                                    java.lang.String r4 = r1.mScpmToken
                                    boolean r4 = android.text.TextUtils.isEmpty(r4)
                                    if (r4 == 0) goto L24
                                    java.lang.String r4 = "loadScpmAgentPolicy try to get new token"
                                    android.util.Slog.i(r3, r4)
                                    java.lang.String r4 = r1.registerAndGetScpmToken()
                                    r1.mScpmToken = r4
                                L24:
                                    java.lang.String r4 = r1.mScpmToken
                                    boolean r4 = android.text.TextUtils.isEmpty(r4)
                                    if (r4 == 0) goto L34
                                    java.lang.String r7 = "loadScpmAgentPolicy fail due to token error"
                                    android.util.Slog.i(r3, r7)
                                    goto Lbe
                                L34:
                                    java.lang.String r4 = r1.mScpmToken     // Catch: java.lang.Exception -> L49
                                    android.os.ParcelFileDescriptor r7 = r1.getScpmParcelFile(r7, r4)     // Catch: java.lang.Exception -> L49
                                    if (r7 != 0) goto L4e
                                    java.lang.String r1 = "loadScpmAgentPolicy pfd is null"
                                    android.util.Slog.i(r3, r1)     // Catch: java.lang.Throwable -> L4c
                                    if (r7 == 0) goto Lbe
                                L44:
                                    r7.close()     // Catch: java.lang.Exception -> L49
                                    goto Lbe
                                L49:
                                    r7 = move-exception
                                    goto Lb8
                                L4c:
                                    r1 = move-exception
                                    goto Lad
                                L4e:
                                    java.io.FileDescriptor r4 = r7.getFileDescriptor()     // Catch: java.lang.Throwable -> L4c
                                    if (r4 == 0) goto L44
                                    java.io.BufferedReader r5 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L4c java.lang.Exception -> L88
                                    java.io.FileReader r6 = new java.io.FileReader     // Catch: java.lang.Throwable -> L4c java.lang.Exception -> L88
                                    r6.<init>(r4)     // Catch: java.lang.Throwable -> L4c java.lang.Exception -> L88
                                    r5.<init>(r6)     // Catch: java.lang.Throwable -> L4c java.lang.Exception -> L88
                                    java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L6d
                                    r4.<init>()     // Catch: java.lang.Throwable -> L6d
                                L63:
                                    java.lang.String r6 = r5.readLine()     // Catch: java.lang.Throwable -> L6d
                                    if (r6 == 0) goto L6f
                                    r4.append(r6)     // Catch: java.lang.Throwable -> L6d
                                    goto L63
                                L6d:
                                    r1 = move-exception
                                    goto L8a
                                L6f:
                                    java.util.List r6 = r2.trustedAgentList     // Catch: java.lang.Throwable -> L6d
                                    java.util.ArrayList r6 = (java.util.ArrayList) r6     // Catch: java.lang.Throwable -> L6d
                                    r6.clear()     // Catch: java.lang.Throwable -> L6d
                                    java.util.List r6 = r2.normalAgentList     // Catch: java.lang.Throwable -> L6d
                                    java.util.ArrayList r6 = (java.util.ArrayList) r6     // Catch: java.lang.Throwable -> L6d
                                    r6.clear()     // Catch: java.lang.Throwable -> L6d
                                    r6 = 0
                                    com.android.server.appfunctions.agentpolicy.AppFunctionAgentPolicyManager.updateAgentList(r4, r2, r6)     // Catch: java.lang.Throwable -> L6d
                                    r1.storeScpmPolicyToFile(r4)     // Catch: java.lang.Throwable -> L6d
                                    r5.close()     // Catch: java.lang.Throwable -> L4c java.lang.Exception -> L88
                                    goto L44
                                L88:
                                    r1 = move-exception
                                    goto L93
                                L8a:
                                    r5.close()     // Catch: java.lang.Throwable -> L8e
                                    goto L92
                                L8e:
                                    r2 = move-exception
                                    r1.addSuppressed(r2)     // Catch: java.lang.Throwable -> L4c java.lang.Exception -> L88
                                L92:
                                    throw r1     // Catch: java.lang.Throwable -> L4c java.lang.Exception -> L88
                                L93:
                                    java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L4c
                                    r2.<init>()     // Catch: java.lang.Throwable -> L4c
                                    java.lang.String r4 = "loadScpmAgentPolicy : "
                                    r2.append(r4)     // Catch: java.lang.Throwable -> L4c
                                    java.lang.String r1 = r1.getMessage()     // Catch: java.lang.Throwable -> L4c
                                    r2.append(r1)     // Catch: java.lang.Throwable -> L4c
                                    java.lang.String r1 = r2.toString()     // Catch: java.lang.Throwable -> L4c
                                    android.util.Slog.e(r3, r1)     // Catch: java.lang.Throwable -> L4c
                                    goto L44
                                Lad:
                                    if (r7 == 0) goto Lb7
                                    r7.close()     // Catch: java.lang.Throwable -> Lb3
                                    goto Lb7
                                Lb3:
                                    r7 = move-exception
                                    r1.addSuppressed(r7)     // Catch: java.lang.Exception -> L49
                                Lb7:
                                    throw r1     // Catch: java.lang.Exception -> L49
                                Lb8:
                                    java.lang.String r1 = "loadScpmAgentPolicy"
                                    android.util.Slog.e(r3, r1, r7)
                                Lbe:
                                    com.android.server.appfunctions.agentpolicy.AppFunctionAgentPolicyManager r7 = com.android.server.appfunctions.agentpolicy.AppFunctionAgentPolicyManager.this
                                    r7.loadUpdatedAgentPolicy()
                                    com.android.server.appfunctions.agentpolicy.AppFunctionAgentPolicyManager r7 = com.android.server.appfunctions.agentpolicy.AppFunctionAgentPolicyManager.this
                                    r7.loadAppliedAgentPolicy()
                                    com.android.server.appfunctions.agentpolicy.AppFunctionAgentPolicyManager r7 = com.android.server.appfunctions.agentpolicy.AppFunctionAgentPolicyManager.this
                                    java.lang.Runnable r7 = r7.mUpdateMetadataRunnable
                                    r7.run()
                                    return
                                */
                                throw new UnsupportedOperationException(
                                        "Method not decompiled:"
                                            + " com.android.server.appfunctions.agentpolicy.AppFunctionAgentPolicyManager$1$$ExternalSyntheticLambda0.run():void");
                            }
                        });
            } else if (KnoxCustomManagerService.ACTION_SCPM_POLICY_CLEAR_DATA.equals(
                    intent.getAction())) {
                boolean z2 = AppFunctionAgentPolicyManager.USER_BUILD;
                Slog.i("AppFunctionAgentPolicyManager", "Cross app action agent policy clear");
                AppFunctionAgentPolicyManager.this.mExecutorService.schedule(
                        new AppFunctionAgentPolicyManager$$ExternalSyntheticLambda0(1, this),
                        60L,
                        TimeUnit.SECONDS);
            } else if ("com.samsung.intent.action.LAZY_BOOT_COMPLETE".equals(intent.getAction())) {
                AppFunctionAgentPolicyManager.this.mExecutorService.execute(
                        new AppFunctionAgentPolicyManager$$ExternalSyntheticLambda0(2, this));
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AgentPolicyInfo {
        public String policyVersion = "unknown";
        public final List trustedAgentList = new ArrayList();
        public final List normalAgentList = new ArrayList();

        public final void copyFrom(AgentPolicyInfo agentPolicyInfo) {
            ((ArrayList) this.trustedAgentList).clear();
            ((ArrayList) this.normalAgentList).clear();
            this.policyVersion = agentPolicyInfo.policyVersion;
            ((ArrayList) this.trustedAgentList).addAll(agentPolicyInfo.trustedAgentList);
            ((ArrayList) this.normalAgentList).addAll(agentPolicyInfo.normalAgentList);
        }
    }

    /* renamed from: -$$Nest$mtryScpmRegister, reason: not valid java name */
    public static void m229$$Nest$mtryScpmRegister(
            AppFunctionAgentPolicyManager appFunctionAgentPolicyManager) {
        appFunctionAgentPolicyManager.getClass();
        try {
            appFunctionAgentPolicyManager.mScpmToken =
                    appFunctionAgentPolicyManager.registerAndGetScpmToken();
            Slog.d(
                    "AppFunctionAgentPolicyManager",
                    "tryScpmRegister mScpmToken : " + appFunctionAgentPolicyManager.mScpmToken);
        } catch (Throwable th) {
            Slog.e("AppFunctionAgentPolicyManager", "tryScpmRegister", th);
        }
    }

    public AppFunctionAgentPolicyManager(
            Context context,
            AppFunctionManagerServiceImpl$$ExternalSyntheticLambda3
                    appFunctionManagerServiceImpl$$ExternalSyntheticLambda3) {
        AnonymousClass1 anonymousClass1 = new AnonymousClass1();
        this.mContext = context;
        this.mPackageManager = context.getPackageManager();
        this.mUpdateMetadataRunnable = appFunctionManagerServiceImpl$$ExternalSyntheticLambda3;
        this.mAppFunctionAgentPolicyCipher = new AppFunctionAgentPolicyCipher(context);
        ScheduledExecutorService newSingleThreadScheduledExecutor =
                Executors.newSingleThreadScheduledExecutor();
        this.mExecutorService = newSingleThreadScheduledExecutor;
        IntentFilter intentFilter =
                new IntentFilter(
                        "com.samsung.android.scpm.policy.UPDATE.cross-app-action-agent-policy");
        intentFilter.addAction(KnoxCustomManagerService.ACTION_SCPM_POLICY_CLEAR_DATA);
        intentFilter.addAction("com.samsung.intent.action.LAZY_BOOT_COMPLETE");
        context.registerReceiver(anonymousClass1, intentFilter, 2);
        newSingleThreadScheduledExecutor.execute(
                new AppFunctionAgentPolicyManager$$ExternalSyntheticLambda0(0, this));
    }

    public static void dumpList(
            IndentingPrintWriter indentingPrintWriter, AgentPolicyInfo agentPolicyInfo) {
        if (USER_BUILD || SHIP_BUILD) {
            return;
        }
        indentingPrintWriter.println("Trusted List : ");
        for (int i = 0; i < ((ArrayList) agentPolicyInfo.trustedAgentList).size(); i++) {
            indentingPrintWriter.println(
                    "  - " + ((String) ((ArrayList) agentPolicyInfo.trustedAgentList).get(i)));
        }
        indentingPrintWriter.println("Normal List : ");
        for (int i2 = 0; i2 < ((ArrayList) agentPolicyInfo.normalAgentList).size(); i2++) {
            indentingPrintWriter.println(
                    "  - " + ((String) ((ArrayList) agentPolicyInfo.normalAgentList).get(i2)));
        }
    }

    public static void updateAgentList(
            StringBuilder sb, AgentPolicyInfo agentPolicyInfo, boolean z) {
        try {
            JSONObject jSONObject = new JSONObject(sb.toString());
            if (jSONObject.has("policy_version")) {
                agentPolicyInfo.policyVersion = jSONObject.getString("policy_version");
                Slog.d(
                        "AppFunctionAgentPolicyManager",
                        "updateAgentList : " + agentPolicyInfo.policyVersion);
            } else {
                Slog.i("AppFunctionAgentPolicyManager", "no version element ");
            }
            if (jSONObject.has("cross_app_agent_trusted_list")) {
                updateList(
                        jSONObject.getJSONArray("cross_app_agent_trusted_list"),
                        agentPolicyInfo.trustedAgentList,
                        z);
            } else {
                Slog.i("AppFunctionAgentPolicyManager", "no trusted agent element ");
            }
            if (jSONObject.has("cross_app_agent_list")) {
                updateList(
                        jSONObject.getJSONArray("cross_app_agent_list"),
                        agentPolicyInfo.normalAgentList,
                        z);
            } else {
                Slog.i("AppFunctionAgentPolicyManager", "no normal agent element ");
            }
        } catch (Exception e) {
            Slog.e("AppFunctionAgentPolicyManager", "updateAgentList", e);
        }
    }

    public static void updateList(JSONArray jSONArray, List list, boolean z) {
        for (int i = 0; i < jSONArray.length(); i++) {
            try {
                String string = jSONArray.getString(i);
                if (z) {
                    list.add(new String(Base64.decode(string, 0)));
                } else {
                    list.add(string);
                }
            } catch (JSONException e) {
                Slog.e("AppFunctionAgentPolicyManager", "updateList", e);
            }
        }
    }

    public final void dump(IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("");
        indentingPrintWriter.println("App Function Agent Policy:");
        indentingPrintWriter.increaseIndent();
        StringBuilder sb = new StringBuilder("Default Version : ");
        AgentPolicyInfo agentPolicyInfo = this.defaultAgentPolicyInfo;
        sb.append(agentPolicyInfo.policyVersion);
        indentingPrintWriter.println(sb.toString());
        dumpList(indentingPrintWriter, agentPolicyInfo);
        indentingPrintWriter.println("");
        StringBuilder sb2 = new StringBuilder("Updated Version : ");
        AgentPolicyInfo agentPolicyInfo2 = this.updatedAgentPolicyInfo;
        sb2.append(agentPolicyInfo2.policyVersion);
        indentingPrintWriter.println(sb2.toString());
        dumpList(indentingPrintWriter, agentPolicyInfo2);
        indentingPrintWriter.println("");
        StringBuilder sb3 = new StringBuilder("SCPM Version : ");
        AgentPolicyInfo agentPolicyInfo3 = this.scpmAgentPolicyInfo;
        sb3.append(agentPolicyInfo3.policyVersion);
        indentingPrintWriter.println(sb3.toString());
        dumpList(indentingPrintWriter, agentPolicyInfo3);
        indentingPrintWriter.println("");
        StringBuilder sb4 = new StringBuilder("Applied Version : ");
        AgentPolicyInfo agentPolicyInfo4 = this.appliedAgentPolicyInfo;
        sb4.append(agentPolicyInfo4.policyVersion);
        indentingPrintWriter.println(sb4.toString());
        dumpList(indentingPrintWriter, agentPolicyInfo4);
        indentingPrintWriter.decreaseIndent();
    }

    public final ParcelFileDescriptor getScpmParcelFile(Context context, String str) {
        Uri parse =
                Uri.parse(
                        "content://com.samsung.android.scpm.policy/"
                                + str
                                + "/cross-app-action-agent-policy");
        try {
            ParcelFileDescriptor openFileDescriptor =
                    context.getContentResolver().openFileDescriptor(parse, "r");
            if (openFileDescriptor != null) {
                return openFileDescriptor;
            }
            Bundle bundle = new Bundle();
            bundle.putString(KnoxCustomManagerService.SPCM_KEY_TOKEN, str);
            Bundle call =
                    this.mContext
                            .getContentResolver()
                            .call(parse, "getLastError", "android", bundle);
            if (call == null) {
                Slog.i("AppFunctionAgentPolicyManager", "getScpmFileDescriptor : bundle is null");
                return null;
            }
            Slog.d(
                    "AppFunctionAgentPolicyManager",
                    "getScpmFileDescriptor : code="
                            + call.getInt(KnoxCustomManagerService.SPCM_KEY_RESULT_CODE, -1)
                            + ", msg="
                            + call.getString(KnoxCustomManagerService.SPCM_KEY_RESULT_MESSAGE));
            return null;
        } catch (Exception e) {
            Slog.e("AppFunctionAgentPolicyManager", "getScpmParcelFile", e);
            return null;
        }
    }

    public final boolean isSystemAppOrPrivilegedApp(String str) {
        boolean z;
        boolean z2;
        ApplicationInfo applicationInfo;
        try {
            applicationInfo = this.mPackageManager.getApplicationInfo(str, 0);
            z = applicationInfo.isSystemApp();
        } catch (PackageManager.NameNotFoundException unused) {
            z = false;
        }
        try {
            z2 = applicationInfo.isPrivilegedApp();
        } catch (PackageManager.NameNotFoundException unused2) {
            Slog.e("AppFunctionAgentPolicyManager", "isSystemAppOrPrivilegedApp package not found");
            z2 = false;
            Slog.d(
                    "AppFunctionAgentPolicyManager",
                    "isSystemAppOrPrivilegedApp isSystemApp : " + z + ", isPrivilegedApp : " + z2);
            if (z) {}
        }
        Slog.d(
                "AppFunctionAgentPolicyManager",
                "isSystemAppOrPrivilegedApp isSystemApp : " + z + ", isPrivilegedApp : " + z2);
        return !z || z2;
    }

    public final void loadAppliedAgentPolicy() {
        synchronized (this.mLock) {
            try {
                if ("unknown".equals(this.updatedAgentPolicyInfo.policyVersion)) {
                    this.appliedAgentPolicyInfo.copyFrom(this.defaultAgentPolicyInfo);
                } else if (this.defaultAgentPolicyInfo.policyVersion.compareTo(
                                this.updatedAgentPolicyInfo.policyVersion)
                        > 0) {
                    this.appliedAgentPolicyInfo.copyFrom(this.defaultAgentPolicyInfo);
                } else {
                    this.appliedAgentPolicyInfo.copyFrom(this.updatedAgentPolicyInfo);
                }
                Slog.i(
                        "AppFunctionAgentPolicyManager",
                        "loadAppliedAgentPolicy appliedAgent version : "
                                + this.appliedAgentPolicyInfo.policyVersion);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void loadUpdatedAgentPolicy() {
        FileInputStream fileInputStream;
        try {
            File file = new File("/data/system/", "updated_agent_policy.json");
            if (!file.exists()) {
                Slog.i("AppFunctionAgentPolicyManager", "loadUpdatedAgentPolicy - no updated file");
                return;
            }
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            AppFunctionAgentPolicyCipher appFunctionAgentPolicyCipher =
                    this.mAppFunctionAgentPolicyCipher;
            appFunctionAgentPolicyCipher.getClass();
            try {
                fileInputStream = new FileInputStream(file);
            } catch (Exception e) {
                Slog.e("AppFunctionAgentPolicyCipher", "decrypt", e);
            }
            try {
                appFunctionAgentPolicyCipher.decrypt(fileInputStream, byteArrayOutputStream);
                fileInputStream.close();
                try {
                    ByteArrayInputStream byteArrayInputStream =
                            new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
                    try {
                        InputStreamReader inputStreamReader =
                                new InputStreamReader(byteArrayInputStream, "UTF-8");
                        try {
                            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                            try {
                                StringBuilder sb = new StringBuilder();
                                while (true) {
                                    String readLine = bufferedReader.readLine();
                                    if (readLine == null) {
                                        synchronized (this.mLock) {
                                            AgentPolicyInfo agentPolicyInfo =
                                                    this.updatedAgentPolicyInfo;
                                            ((ArrayList) agentPolicyInfo.trustedAgentList).clear();
                                            ((ArrayList) agentPolicyInfo.normalAgentList).clear();
                                            updateAgentList(sb, this.updatedAgentPolicyInfo, false);
                                        }
                                        bufferedReader.close();
                                        inputStreamReader.close();
                                        byteArrayInputStream.close();
                                        return;
                                    }
                                    sb.append(readLine);
                                }
                            } finally {
                            }
                        } finally {
                        }
                    } finally {
                    }
                } catch (Exception e2) {
                    Slog.e("AppFunctionAgentPolicyManager", "loadUpdatedAgentPolicy", e2);
                }
            } finally {
            }
        } catch (Exception e3) {
            Slog.e("AppFunctionAgentPolicyManager", "loadUpdatedAgentPolicy", e3);
        }
    }

    public final String registerAndGetScpmToken() {
        if (this.mContext
                        .getPackageManager()
                        .resolveContentProvider(KnoxCustomManagerService.SPCM_PROVIDER_AUTHORITY, 0)
                != null) {
            try {
                Bundle bundle = new Bundle();
                bundle.putString("packageName", "android");
                bundle.putString("appId", "ryyegi2x7b");
                bundle.putString("version", "1.0.0");
                bundle.putString("receiverPackageName", "android");
                Bundle call =
                        this.mContext
                                .getContentResolver()
                                .call(this.SCPM_URI_V2, "register", "android", bundle);
                if (call != null) {
                    int i = call.getInt(KnoxCustomManagerService.SPCM_KEY_RESULT, 1);
                    String string = call.getString(KnoxCustomManagerService.SPCM_KEY_TOKEN, null);
                    int i2 = call.getInt(KnoxCustomManagerService.SPCM_KEY_RESULT_CODE, -1);
                    String string2 =
                            call.getString(KnoxCustomManagerService.SPCM_KEY_RESULT_MESSAGE, "");
                    if (i == 1) {
                        Slog.i(
                                "AppFunctionAgentPolicyManager",
                                "registerAndGetScpmToken success to get the token");
                        return string;
                    }
                    Slog.i(
                            "AppFunctionAgentPolicyManager",
                            "registerAndGetScpmToken failed to register: rCode = "
                                    + i2
                                    + ", rMsg = "
                                    + string2);
                    return null;
                }
            } catch (Exception e) {
                Slog.e(
                        "AppFunctionAgentPolicyManager",
                        "registerAndGetScpmToken cannot register package",
                        e);
            }
        } else {
            Slog.d("AppFunctionAgentPolicyManager", "scpm service is not available");
        }
        return null;
    }

    public final void storeScpmPolicyToFile(StringBuilder sb) {
        AgentPolicyInfo agentPolicyInfo = this.updatedAgentPolicyInfo;
        if (!"unknown".equals(agentPolicyInfo.policyVersion)
                && this.scpmAgentPolicyInfo.policyVersion.compareTo(agentPolicyInfo.policyVersion)
                        <= 0) {
            Slog.i("AppFunctionAgentPolicyManager", "storeScpmPolicyToFile ignore");
            return;
        }
        try {
            ByteArrayInputStream byteArrayInputStream =
                    new ByteArrayInputStream(sb.toString().getBytes());
            try {
                File file = new File("/data/system/", "updated_agent_policy_temp.json");
                AppFunctionAgentPolicyCipher appFunctionAgentPolicyCipher =
                        this.mAppFunctionAgentPolicyCipher;
                appFunctionAgentPolicyCipher.getClass();
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    try {
                        appFunctionAgentPolicyCipher.encrypt(
                                byteArrayInputStream, fileOutputStream);
                        fileOutputStream.close();
                    } finally {
                    }
                } catch (Throwable th) {
                    Slog.e("AppFunctionAgentPolicyCipher", "encrypt", th);
                }
                File file2 = new File("/data/system/", "updated_agent_policy.json");
                if (file2.exists() && !file2.delete()) {
                    Slog.e(
                            "AppFunctionAgentPolicyManager",
                            "storeScpmPolicyToFile - original file deletion failed");
                }
                if (!file.renameTo(file2)) {
                    Slog.e(
                            "AppFunctionAgentPolicyManager",
                            "storeScpmPolicyToFile - temp file rename failed");
                }
                byteArrayInputStream.close();
            } finally {
            }
        } catch (Exception e) {
            Slog.e("AppFunctionAgentPolicyManager", "storeScpmPolicyToFile", e);
        }
    }
}
