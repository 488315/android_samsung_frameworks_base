package com.samsung.android.server.pm.allowlist;

import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Slog;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.samsung.android.rune.PMRune;
import com.samsung.android.server.pm.PmLog;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* loaded from: classes2.dex */
public class RestrictedReceiverFilter {
    public static RestrictedReceiverFilter sInstance;
    public Object mLock = new Object();
    public final List mRestrictedActions = new ArrayList();
    public final Map mAllowedItems = new ArrayMap();
    public final Map mViolationActions = new ArrayMap();
    public final Set mExemptedPackageNames = new HashSet();
    public final List mExemptedPackagePrefixNames = new ArrayList();
    public final Set mRestrictedPackageNames = new HashSet();
    public final List mRestrictedPackagePrefixNames = new ArrayList();
    public final Map mViolationCodePaths = new ArrayMap();
    public boolean mEnabled = false;

    public static RestrictedReceiverFilter getInstance() {
        if (sInstance == null) {
            synchronized (RestrictedReceiverFilter.class) {
                if (sInstance == null) {
                    sInstance = new RestrictedReceiverFilter();
                }
            }
        }
        return sInstance;
    }

    public void enableAndConfigure(boolean z) {
        this.mEnabled = z;
        synchronized (this.mLock) {
            if (this.mEnabled) {
                clearItemsLocked();
                loadItemsLocked();
            } else {
                clearItemsLocked();
            }
        }
    }

    public final void loadItemsLocked() {
        loadItemsInternalLocked(null);
    }

    public void loadItemsInternalLocked(String str) {
        BroadcastReceiverListParser broadcastReceiverListParser;
        if (BroadcastReceiverListParser.FW_BR_ALLOW_LIST_WITH_SCPM) {
            broadcastReceiverListParser = new BroadcastReceiverListParserWithScpm();
        } else {
            broadcastReceiverListParser = new BroadcastReceiverListParser();
        }
        if (str == null) {
            broadcastReceiverListParser.parseAllowList();
        } else {
            broadcastReceiverListParser.parseAllowList(str);
        }
        this.mAllowedItems.putAll(broadcastReceiverListParser.getPackageMap());
        this.mRestrictedActions.addAll(broadcastReceiverListParser.getRestricedIntent());
        this.mExemptedPackageNames.addAll(broadcastReceiverListParser.getAllowedPackageNames());
        this.mExemptedPackagePrefixNames.addAll(broadcastReceiverListParser.getAllowedPackagePrefixNames());
        this.mRestrictedPackageNames.addAll(broadcastReceiverListParser.getRestrictedPackageNames());
        this.mRestrictedPackagePrefixNames.addAll(broadcastReceiverListParser.getRestrictedPackagePrefixNames());
        boolean isWorkCompChangedEnabled = broadcastReceiverListParser.isWorkCompChangedEnabled();
        if (PMRune.PM_WA_WORK_COMP_CHANGED != isWorkCompChangedEnabled) {
            PMRune.PM_WA_WORK_COMP_CHANGED = isWorkCompChangedEnabled;
            PmLog.logDebugInfoAndLogcat("PM_WA_WORK_COMP_CHANGED change to " + isWorkCompChangedEnabled);
        }
    }

    public void clearItemsLocked() {
        this.mAllowedItems.clear();
        this.mRestrictedActions.clear();
        this.mExemptedPackageNames.clear();
        this.mExemptedPackagePrefixNames.clear();
        this.mRestrictedPackageNames.clear();
        this.mRestrictedPackagePrefixNames.clear();
    }

    public boolean filterReceiver(String str, String str2, String str3) {
        if (!this.mEnabled) {
            return false;
        }
        synchronized (this.mLock) {
            if (!this.mRestrictedActions.contains(str3)) {
                return false;
            }
            if (!isExemptedPackageLocked(str) && isRestrictedPackageLocked(str)) {
                return isAllowedActionLocked(str, str3) ? false : true;
            }
            return false;
        }
    }

    public boolean isRestrictedPackageLocked(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        Iterator it = this.mRestrictedPackagePrefixNames.iterator();
        while (it.hasNext()) {
            if (str.startsWith((String) it.next())) {
                return true;
            }
        }
        return this.mRestrictedPackageNames.contains(str);
    }

    public boolean isAllowedActionLocked(String str, String str2) {
        Set set;
        return (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || (set = (Set) this.mAllowedItems.get(str)) == null || !set.contains(str2)) ? false : true;
    }

    public final boolean isExemptedPackageLocked(String str) {
        if (this.mExemptedPackageNames.contains(str)) {
            return true;
        }
        Iterator it = this.mExemptedPackagePrefixNames.iterator();
        while (it.hasNext()) {
            if (str.startsWith((String) it.next())) {
                return true;
            }
        }
        return false;
    }

    public void addViolationLog(String str, String str2, String str3) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3)) {
            return;
        }
        Slog.d("RestrictedReceiverFilter", "Restricted action " + str3 + " for package " + str);
        synchronized (this.mLock) {
            List list = (List) this.mViolationActions.get(str);
            if (list == null) {
                list = new ArrayList();
            }
            if (!list.contains(str3)) {
                list.add(str3);
            }
            this.mViolationCodePaths.put(str, str2);
            this.mViolationActions.put(str, list);
        }
    }

    public String getViolationLog() {
        final StringBuilder sb = new StringBuilder(1000);
        sb.append("Restricted receiver violations:\n");
        synchronized (this.mLock) {
            if (this.mViolationActions.size() == 0) {
                return "No Restricted receiver violations";
            }
            this.mViolationActions.forEach(new BiConsumer() { // from class: com.samsung.android.server.pm.allowlist.RestrictedReceiverFilter$$ExternalSyntheticLambda0
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    RestrictedReceiverFilter.this.lambda$getViolationLog$0(sb, (String) obj, (List) obj2);
                }
            });
            return sb.toString();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getViolationLog$0(StringBuilder sb, String str, List list) {
        sb.append("[" + str + "]\n");
        sb.append("    path: " + ((String) this.mViolationCodePaths.get(str)) + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        sb.append("    violations:\n");
        Iterator it = list.iterator();
        while (it.hasNext()) {
            sb.append("        " + ((String) it.next()) + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        }
    }

    public void logViolationsIfNeeded(Consumer consumer) {
        String violationLog = getViolationLog();
        if (TextUtils.isEmpty(violationLog)) {
            return;
        }
        consumer.accept(violationLog);
    }

    public class RestrictedAction {
        public String mAction;
        public String mCodePath;
        public String mPackageName;

        public RestrictedAction(String str, String str2, String str3) {
            this.mAction = str;
            this.mPackageName = str2;
            this.mCodePath = str3;
        }
    }
}
