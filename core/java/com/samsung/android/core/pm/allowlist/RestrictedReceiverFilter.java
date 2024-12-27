package com.samsung.android.core.pm.allowlist;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Slog;

import com.samsung.android.rune.PMRune;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class RestrictedReceiverFilter {
    private static final boolean DEBUG = true;
    private static final String TAG = "RestrictedReceiverFilter";
    private static RestrictedReceiverFilter sInstance;
    private boolean mEnabled;
    private Object mLock = new Object();
    private final List<String> mRestrictedActions = new ArrayList();
    private final Map<String, Set<String>> mAllowedItems = new ArrayMap();
    private final Map<String, List<String>> mViolationActions = new ArrayMap();
    private final Set<String> mExemptedPackageNames = new HashSet();
    private final List<String> mExemptedPackagePrefixNames = new ArrayList();
    private final Set<String> mRestrictedPackageNames = new HashSet();
    private final List<String> mRestrictedPackagePrefixNames = new ArrayList();
    private final Map<String, String> mViolationCodePaths = new ArrayMap();

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

    private RestrictedReceiverFilter() {
        this.mEnabled = false;
        this.mEnabled = false;
    }

    public void enableAndConfigure(boolean enable) {
        this.mEnabled = enable;
        synchronized (this.mLock) {
            if (this.mEnabled) {
                clearItemsLocked();
                loadItemsLocked();
            } else {
                clearItemsLocked();
            }
        }
    }

    private void loadItemsLocked() {
        loadItemsInternalLocked(null);
    }

    public void loadItemsInternalLocked(String path) {
        BroadcastReceiverListParser parser;
        if (BroadcastReceiverListParser.FW_BR_ALLOW_LIST_WITH_SCPM) {
            parser = new BroadcastReceiverListParserWithScpm();
        } else {
            parser = new BroadcastReceiverListParser();
        }
        if (path == null) {
            parser.parseAllowList();
        } else {
            parser.parseAllowList(path);
        }
        this.mAllowedItems.putAll(parser.getPackageMap());
        this.mRestrictedActions.addAll(parser.getRestricedIntent());
        this.mExemptedPackageNames.addAll(parser.getAllowedPackageNames());
        this.mExemptedPackagePrefixNames.addAll(parser.getAllowedPackagePrefixNames());
        this.mRestrictedPackageNames.addAll(parser.getRestrictedPackageNames());
        this.mRestrictedPackagePrefixNames.addAll(parser.getRestrictedPackagePrefixNames());
        boolean isWorkCompChangedEnabled = parser.isWorkCompChangedEnabled();
        if (PMRune.PM_WA_WORK_COMP_CHANGED != isWorkCompChangedEnabled) {
            PMRune.PM_WA_WORK_COMP_CHANGED = isWorkCompChangedEnabled;
            Slog.d(TAG, "PM_WA_WORK_COMP_CHANGED change to " + isWorkCompChangedEnabled);
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

    public boolean filterReceiver(String packageName, String action) {
        if (!this.mEnabled) {
            return false;
        }
        synchronized (this.mLock) {
            if (!this.mRestrictedActions.contains(action)) {
                return false;
            }
            if (!isExemptedPackageLocked(packageName) && isRestrictedPackageLocked(packageName)) {
                return isAllowedActionLocked(packageName, action) ? false : true;
            }
            return false;
        }
    }

    public boolean isRestrictedPackageLocked(String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return false;
        }
        for (String prefix : this.mRestrictedPackagePrefixNames) {
            if (packageName.startsWith(prefix)) {
                return true;
            }
        }
        return this.mRestrictedPackageNames.contains(packageName);
    }

    public boolean isAllowedActionLocked(String packageName, String action) {
        Set<String> allowedActions;
        return (TextUtils.isEmpty(packageName)
                        || TextUtils.isEmpty(action)
                        || (allowedActions = this.mAllowedItems.get(packageName)) == null
                        || !allowedActions.contains(action))
                ? false
                : true;
    }

    private boolean isExemptedPackageLocked(String packageName) {
        if (this.mExemptedPackageNames.contains(packageName)) {
            return true;
        }
        for (String prefix : this.mExemptedPackagePrefixNames) {
            if (packageName.startsWith(prefix)) {
                return true;
            }
        }
        return false;
    }

    public void addViolationLog(String packageName, String codePath, String action) {
        if (TextUtils.isEmpty(packageName)
                || TextUtils.isEmpty(codePath)
                || TextUtils.isEmpty(action)) {
            return;
        }
        String msg = "Restricted action " + action + " for package " + packageName;
        Slog.d(TAG, msg);
        synchronized (this.mLock) {
            List<String> violations = this.mViolationActions.get(packageName);
            if (violations == null) {
                violations = new ArrayList();
            }
            if (!violations.contains(action)) {
                violations.add(action);
            }
            this.mViolationCodePaths.put(packageName, codePath);
            this.mViolationActions.put(packageName, violations);
        }
    }

    public String getViolationLog() {
        final StringBuilder sb = new StringBuilder(1000);
        sb.append("Restricted receiver violations:\n");
        synchronized (this.mLock) {
            if (this.mViolationActions.size() == 0) {
                return "No Restricted receiver violations";
            }
            this.mViolationActions.forEach(
                    new BiConsumer() { // from class:
                                       // com.samsung.android.core.pm.allowlist.RestrictedReceiverFilter$$ExternalSyntheticLambda0
                        @Override // java.util.function.BiConsumer
                        public final void accept(Object obj, Object obj2) {
                            RestrictedReceiverFilter.this.lambda$getViolationLog$0(
                                    sb, (String) obj, (List) obj2);
                        }
                    });
            return sb.toString();
        }
    }

    public /* synthetic */ void lambda$getViolationLog$0(
            StringBuilder sb, String packageName, List actions) {
        sb.append(NavigationBarInflaterView.SIZE_MOD_START + packageName + "]\n");
        sb.append("    path: " + this.mViolationCodePaths.get(packageName) + "\n");
        sb.append("    violations:\n");
        Iterator it = actions.iterator();
        while (it.hasNext()) {
            String action = (String) it.next();
            sb.append("        " + action + "\n");
        }
    }

    public void logViolationsIfNeeded(Consumer<String> logger) {
        String msg = getViolationLog();
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        logger.accept(msg);
    }

    public static class RestrictedAction {
        public String mAction;
        public String mCodePath;
        public String mPackageName;

        public RestrictedAction(String action, String packageName, String codePath) {
            this.mAction = action;
            this.mPackageName = packageName;
            this.mCodePath = codePath;
        }
    }
}
