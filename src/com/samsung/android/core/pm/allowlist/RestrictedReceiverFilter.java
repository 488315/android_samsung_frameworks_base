package com.samsung.android.core.pm.allowlist;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Slog;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import com.samsung.android.rune.PMRune;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes6.dex */
public class RestrictedReceiverFilter {
    private static final boolean DEBUG = true;
    private static final String TAG = "RestrictedReceiverFilter";
    private static RestrictedReceiverFilter sInstance;
    private Object mLock = new Object();
    private final List<String> mRestrictedActions = new ArrayList();
    private final Map<String, Set<String>> mAllowedItems = new ArrayMap();
    private final Map<String, List<String>> mViolationActions = new ArrayMap();
    private final Set<String> mExemptedPackageNames = new HashSet();
    private final List<String> mExemptedPackagePrefixNames = new ArrayList();
    private final Set<String> mRestrictedPackageNames = new HashSet();
    private final List<String> mRestrictedPackagePrefixNames = new ArrayList();
    private final Map<String, String> mViolationCodePaths = new ArrayMap();
    private boolean mEnabled = false;

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

    private void loadItemsLocked() throws XmlPullParserException, IOException {
        loadItemsInternalLocked(null);
    }

    public void loadItemsInternalLocked(String str) throws XmlPullParserException, IOException {
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
        boolean zIsWorkCompChangedEnabled = broadcastReceiverListParser.isWorkCompChangedEnabled();
        if (PMRune.PM_WA_WORK_COMP_CHANGED != zIsWorkCompChangedEnabled) {
            PMRune.PM_WA_WORK_COMP_CHANGED = zIsWorkCompChangedEnabled;
            Slog.d(TAG, "PM_WA_WORK_COMP_CHANGED change to " + zIsWorkCompChangedEnabled);
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

    public boolean filterReceiver(String str, String str2) {
        if (!this.mEnabled) {
            return false;
        }
        synchronized (this.mLock) {
            if (!this.mRestrictedActions.contains(str2)) {
                return false;
            }
            if (!isExemptedPackageLocked(str) && isRestrictedPackageLocked(str)) {
                return !isAllowedActionLocked(str, str2);
            }
            return false;
        }
    }

    public boolean isRestrictedPackageLocked(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        Iterator<String> it = this.mRestrictedPackagePrefixNames.iterator();
        while (it.hasNext()) {
            if (str.startsWith(it.next())) {
                return true;
            }
        }
        return this.mRestrictedPackageNames.contains(str);
    }

    public boolean isAllowedActionLocked(String str, String str2) {
        Set<String> set;
        return (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || (set = this.mAllowedItems.get(str)) == null || !set.contains(str2)) ? false : true;
    }

    private boolean isExemptedPackageLocked(String str) {
        if (this.mExemptedPackageNames.contains(str)) {
            return true;
        }
        Iterator<String> it = this.mExemptedPackagePrefixNames.iterator();
        while (it.hasNext()) {
            if (str.startsWith(it.next())) {
                return true;
            }
        }
        return false;
    }

    public void addViolationLog(String str, String str2, String str3) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3)) {
            return;
        }
        Slog.d(TAG, "Restricted action " + str3 + " for package " + str);
        synchronized (this.mLock) {
            List<String> arrayList = this.mViolationActions.get(str);
            if (arrayList == null) {
                arrayList = new ArrayList<>();
            }
            if (!arrayList.contains(str3)) {
                arrayList.add(str3);
            }
            this.mViolationCodePaths.put(str, str2);
            this.mViolationActions.put(str, arrayList);
        }
    }

    public String getViolationLog() {
        final StringBuilder sb = new StringBuilder(1000);
        sb.append("Restricted receiver violations:\n");
        synchronized (this.mLock) {
            if (this.mViolationActions.size() == 0) {
                return "No Restricted receiver violations";
            }
            this.mViolationActions.forEach(new BiConsumer() { // from class: com.samsung.android.core.pm.allowlist.RestrictedReceiverFilter$$ExternalSyntheticLambda0
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    this.f$0.lambda$getViolationLog$0(sb, (String) obj, (List) obj2);
                }
            });
            return sb.toString();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getViolationLog$0(StringBuilder sb, String str, List list) {
        sb.append(NavigationBarInflaterView.SIZE_MOD_START + str + "]\n");
        sb.append("    path: " + this.mViolationCodePaths.get(str) + ShaderAssembler.NEWLINE);
        sb.append("    violations:\n");
        Iterator it = list.iterator();
        while (it.hasNext()) {
            sb.append("        " + ((String) it.next()) + ShaderAssembler.NEWLINE);
        }
    }

    public void logViolationsIfNeeded(Consumer<String> consumer) {
        String violationLog = getViolationLog();
        if (TextUtils.isEmpty(violationLog)) {
            return;
        }
        consumer.accept(violationLog);
    }

    public static class RestrictedAction {
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
