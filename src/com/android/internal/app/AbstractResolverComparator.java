package com.android.internal.app;

import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.BadParcelableException;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.UserHandle;
import android.util.Log;
import com.android.internal.app.ResolverActivity;
import com.android.internal.app.chooser.TargetInfo;
import com.google.android.collect.Lists;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes5.dex */
public abstract class AbstractResolverComparator implements Comparator<ResolverActivity.ResolvedComponentInfo> {
    private static final boolean DEBUG = true;
    private static final int NUM_OF_TOP_ANNOTATIONS_TO_USE = 3;
    static final int RANKER_RESULT_TIMEOUT = 1;
    static final int RANKER_SERVICE_RESULT = 0;
    private static final String TAG = "AbstractResolverComp";
    private static final int WATCHDOG_TIMEOUT_MILLIS = 500;
    protected AfterCompute mAfterCompute;
    protected String[] mAnnotations;
    private final Comparator<ResolveInfo> mAzComparator;
    private ChooserActivityLogger mChooserActivityLogger;
    protected String mContentType;
    protected final Handler mHandler;
    private final boolean mHttp;
    protected final Map<UserHandle, PackageManager> mPmMap;
    protected final Map<UserHandle, UsageStatsManager> mUsmMap;

    interface AfterCompute {
        void afterCompute();
    }

    abstract int compare(ResolveInfo resolveInfo, ResolveInfo resolveInfo2);

    abstract void doCompute(List<ResolverActivity.ResolvedComponentInfo> list);

    abstract float getScore(TargetInfo targetInfo);

    abstract void handleResultMessage(Message message);

    void updateModel(TargetInfo targetInfo) {
    }

    public AbstractResolverComparator(Context context, Intent intent, UserHandle userHandle) {
        this(context, intent, Lists.newArrayList(userHandle));
    }

    public AbstractResolverComparator(Context context, Intent intent, List<UserHandle> list) {
        this.mPmMap = new HashMap();
        this.mUsmMap = new HashMap();
        this.mHandler = new Handler(Looper.getMainLooper()) { // from class: com.android.internal.app.AbstractResolverComparator.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                int i = message.what;
                if (i == 0) {
                    Log.d(AbstractResolverComparator.TAG, "RANKER_SERVICE_RESULT");
                    if (AbstractResolverComparator.this.mHandler.hasMessages(1)) {
                        AbstractResolverComparator.this.handleResultMessage(message);
                        AbstractResolverComparator.this.mHandler.removeMessages(1);
                        AbstractResolverComparator.this.afterCompute();
                        return;
                    }
                    return;
                }
                if (i == 1) {
                    Log.d(AbstractResolverComparator.TAG, "RANKER_RESULT_TIMEOUT; unbinding services");
                    AbstractResolverComparator.this.mHandler.removeMessages(0);
                    AbstractResolverComparator.this.afterCompute();
                    if (AbstractResolverComparator.this.mChooserActivityLogger != null) {
                        AbstractResolverComparator.this.mChooserActivityLogger.logSharesheetAppShareRankingTimeout();
                        return;
                    }
                    return;
                }
                super.handleMessage(message);
            }
        };
        String scheme = intent.getScheme();
        this.mHttp = IntentFilter.SCHEME_HTTP.equals(scheme) || IntentFilter.SCHEME_HTTPS.equals(scheme);
        this.mContentType = intent.getType();
        getContentAnnotations(intent);
        for (UserHandle userHandle : list) {
            Context createContextAsUser = context.createContextAsUser(userHandle, 0);
            this.mPmMap.put(userHandle, createContextAsUser.getPackageManager());
            this.mUsmMap.put(userHandle, (UsageStatsManager) createContextAsUser.getSystemService(Context.USAGE_STATS_SERVICE));
        }
        this.mAzComparator = new AzInfoComparator(this, context);
    }

    private void getContentAnnotations(Intent intent) {
        try {
            ArrayList<String> stringArrayListExtra = intent.getStringArrayListExtra(Intent.EXTRA_CONTENT_ANNOTATIONS);
            if (stringArrayListExtra != null) {
                int size = stringArrayListExtra.size();
                if (size > 3) {
                    size = 3;
                }
                this.mAnnotations = new String[size];
                for (int i = 0; i < size; i++) {
                    this.mAnnotations[i] = stringArrayListExtra.get(i);
                }
            }
        } catch (BadParcelableException unused) {
            Log.i(TAG, "Couldn't unparcel intent annotations. Ignoring.");
            this.mAnnotations = new String[0];
        }
    }

    void setCallBack(AfterCompute afterCompute) {
        this.mAfterCompute = afterCompute;
    }

    void setChooserActivityLogger(ChooserActivityLogger chooserActivityLogger) {
        this.mChooserActivityLogger = chooserActivityLogger;
    }

    ChooserActivityLogger getChooserActivityLogger() {
        return this.mChooserActivityLogger;
    }

    protected final void afterCompute() {
        AfterCompute afterCompute = this.mAfterCompute;
        if (afterCompute != null) {
            afterCompute.afterCompute();
        }
    }

    @Override // java.util.Comparator
    public final int compare(ResolverActivity.ResolvedComponentInfo resolvedComponentInfo, ResolverActivity.ResolvedComponentInfo resolvedComponentInfo2) {
        boolean isSpecificUriMatch;
        ResolveInfo resolveInfoAt = resolvedComponentInfo.getResolveInfoAt(0);
        ResolveInfo resolveInfoAt2 = resolvedComponentInfo2.getResolveInfoAt(0);
        boolean isFixedAtTop = resolvedComponentInfo.isFixedAtTop();
        boolean isFixedAtTop2 = resolvedComponentInfo2.isFixedAtTop();
        if (isFixedAtTop && !isFixedAtTop2) {
            return -1;
        }
        if (!isFixedAtTop && isFixedAtTop2) {
            return 1;
        }
        if (resolveInfoAt.targetUserId != -2) {
            return resolveInfoAt2.targetUserId != -2 ? 0 : 1;
        }
        if (resolveInfoAt2.targetUserId != -2) {
            return -1;
        }
        if (this.mHttp && (isSpecificUriMatch = ResolverActivity.isSpecificUriMatch(resolveInfoAt.match)) != ResolverActivity.isSpecificUriMatch(resolveInfoAt2.match)) {
            return isSpecificUriMatch ? -1 : 1;
        }
        boolean isPinned = resolvedComponentInfo.isPinned();
        boolean isPinned2 = resolvedComponentInfo2.isPinned();
        if (isPinned && !isPinned2) {
            return -1;
        }
        if (!isPinned && isPinned2) {
            return 1;
        }
        if (isPinned && isPinned2) {
            return this.mAzComparator.compare(resolvedComponentInfo.getResolveInfoAt(0), resolvedComponentInfo2.getResolveInfoAt(0));
        }
        return compare(resolveInfoAt, resolveInfoAt2);
    }

    final void compute(List<ResolverActivity.ResolvedComponentInfo> list) {
        beforeCompute();
        doCompute(list);
    }

    final void updateChooserCounts(String str, UserHandle userHandle, String str2) {
        if (this.mUsmMap.containsKey(userHandle)) {
            this.mUsmMap.get(userHandle).reportChooserSelection(str, userHandle.getIdentifier(), this.mContentType, this.mAnnotations, str2);
        }
    }

    void beforeCompute() {
        Log.d(TAG, "Setting watchdog timer for 500ms");
        Handler handler = this.mHandler;
        if (handler == null) {
            Log.d(TAG, "Error: Handler is Null; Needs to be initialized.");
        } else {
            handler.sendEmptyMessageDelayed(1, 500L);
        }
    }

    void destroy() {
        this.mHandler.removeMessages(0);
        this.mHandler.removeMessages(1);
        afterCompute();
        this.mAfterCompute = null;
    }

    class AzInfoComparator implements Comparator<ResolveInfo> {
        Collator mCollator;

        AzInfoComparator(AbstractResolverComparator abstractResolverComparator, Context context) {
            this.mCollator = Collator.getInstance(context.getResources().getConfiguration().locale);
        }

        @Override // java.util.Comparator
        public int compare(ResolveInfo resolveInfo, ResolveInfo resolveInfo2) {
            if (resolveInfo == null) {
                return -1;
            }
            if (resolveInfo2 == null) {
                return 1;
            }
            return this.mCollator.compare(resolveInfo.activityInfo.packageName, resolveInfo2.activityInfo.packageName);
        }
    }
}
