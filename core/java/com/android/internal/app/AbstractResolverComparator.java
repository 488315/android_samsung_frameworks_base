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

    public AbstractResolverComparator(Context launchedFromContext, Intent intent, UserHandle targetUserSpace) {
        this(launchedFromContext, intent, Lists.newArrayList(targetUserSpace));
    }

    public AbstractResolverComparator(Context launchedFromContext, Intent intent, List<UserHandle> targetUserSpaceList) {
        this.mPmMap = new HashMap();
        this.mUsmMap = new HashMap();
        this.mHandler = new Handler(Looper.getMainLooper()) { // from class: com.android.internal.app.AbstractResolverComparator.1
            @Override // android.os.Handler
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        Log.d(AbstractResolverComparator.TAG, "RANKER_SERVICE_RESULT");
                        if (AbstractResolverComparator.this.mHandler.hasMessages(1)) {
                            AbstractResolverComparator.this.handleResultMessage(msg);
                            AbstractResolverComparator.this.mHandler.removeMessages(1);
                            AbstractResolverComparator.this.afterCompute();
                            break;
                        }
                        break;
                    case 1:
                        Log.d(AbstractResolverComparator.TAG, "RANKER_RESULT_TIMEOUT; unbinding services");
                        AbstractResolverComparator.this.mHandler.removeMessages(0);
                        AbstractResolverComparator.this.afterCompute();
                        if (AbstractResolverComparator.this.mChooserActivityLogger != null) {
                            AbstractResolverComparator.this.mChooserActivityLogger.logSharesheetAppShareRankingTimeout();
                            break;
                        }
                        break;
                    default:
                        super.handleMessage(msg);
                        break;
                }
            }
        };
        String scheme = intent.getScheme();
        this.mHttp = IntentFilter.SCHEME_HTTP.equals(scheme) || IntentFilter.SCHEME_HTTPS.equals(scheme);
        this.mContentType = intent.getType();
        getContentAnnotations(intent);
        for (UserHandle user : targetUserSpaceList) {
            Context userContext = launchedFromContext.createContextAsUser(user, 0);
            this.mPmMap.put(user, userContext.getPackageManager());
            this.mUsmMap.put(user, (UsageStatsManager) userContext.getSystemService(Context.USAGE_STATS_SERVICE));
        }
        this.mAzComparator = new AzInfoComparator(launchedFromContext);
    }

    private void getContentAnnotations(Intent intent) {
        try {
            ArrayList<String> annotations = intent.getStringArrayListExtra(Intent.EXTRA_CONTENT_ANNOTATIONS);
            if (annotations != null) {
                int size = annotations.size();
                if (size > 3) {
                    size = 3;
                }
                this.mAnnotations = new String[size];
                for (int i = 0; i < size; i++) {
                    this.mAnnotations[i] = annotations.get(i);
                }
            }
        } catch (BadParcelableException e) {
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
    public final int compare(ResolverActivity.ResolvedComponentInfo lhsp, ResolverActivity.ResolvedComponentInfo rhsp) {
        ResolveInfo lhs = lhsp.getResolveInfoAt(0);
        ResolveInfo rhs = rhsp.getResolveInfoAt(0);
        boolean lFixedAtTop = lhsp.isFixedAtTop();
        boolean rFixedAtTop = rhsp.isFixedAtTop();
        if (lFixedAtTop && !rFixedAtTop) {
            return -1;
        }
        if (!lFixedAtTop && rFixedAtTop) {
            return 1;
        }
        if (lhs.targetUserId != -2) {
            if (rhs.targetUserId != -2) {
                return 0;
            }
            return 1;
        }
        if (rhs.targetUserId != -2) {
            return -1;
        }
        if (this.mHttp) {
            boolean lhsSpecific = ResolverActivity.isSpecificUriMatch(lhs.match);
            boolean rhsSpecific = ResolverActivity.isSpecificUriMatch(rhs.match);
            if (lhsSpecific != rhsSpecific) {
                if (lhsSpecific) {
                    return -1;
                }
                return 1;
            }
        }
        boolean lPinned = lhsp.isPinned();
        boolean rPinned = rhsp.isPinned();
        if (lPinned && !rPinned) {
            return -1;
        }
        if (!lPinned && rPinned) {
            return 1;
        }
        if (lPinned && rPinned) {
            return this.mAzComparator.compare(lhsp.getResolveInfoAt(0), rhsp.getResolveInfoAt(0));
        }
        return compare(lhs, rhs);
    }

    final void compute(List<ResolverActivity.ResolvedComponentInfo> targets) {
        beforeCompute();
        doCompute(targets);
    }

    final void updateChooserCounts(String packageName, UserHandle user, String action) {
        if (this.mUsmMap.containsKey(user)) {
            this.mUsmMap.get(user).reportChooserSelection(packageName, user.getIdentifier(), this.mContentType, this.mAnnotations, action);
        }
    }

    void updateModel(TargetInfo targetInfo) {
    }

    void beforeCompute() {
        Log.d(TAG, "Setting watchdog timer for 500ms");
        if (this.mHandler == null) {
            Log.d(TAG, "Error: Handler is Null; Needs to be initialized.");
        } else {
            this.mHandler.sendEmptyMessageDelayed(1, 500L);
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

        AzInfoComparator(Context context) {
            this.mCollator = Collator.getInstance(context.getResources().getConfiguration().locale);
        }

        @Override // java.util.Comparator
        public int compare(ResolveInfo lhsp, ResolveInfo rhsp) {
            if (lhsp == null) {
                return -1;
            }
            if (rhsp == null) {
                return 1;
            }
            return this.mCollator.compare(lhsp.activityInfo.packageName, rhsp.activityInfo.packageName);
        }
    }
}
