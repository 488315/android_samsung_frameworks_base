package com.android.systemui.statusbar;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.ArrayMap;
import android.util.Log;
import androidx.picker.adapter.AbsAdapter$1$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.NotiRune;
import com.android.systemui.shade.ShadeExpansionStateManager;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.util.SettingsHelper;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import notification.src.com.android.systemui.BasePromptProcessor;
import notification.src.com.android.systemui.GaussCloudPromptProcessor;
import notification.src.com.android.systemui.PromptCallback;
import notification.src.com.android.systemui.SrPromptProcessor;

public final class SummarizeController {
    public final Lazy mCommonNotifCollectionLazy;
    public final Context mContext;
    public final BasePromptProcessor mPromptProcessor;
    public final ShadeExpansionStateManager mShadeExpansionManager;
    public final boolean mSummarizeEnabled;
    public final Map mNotificationSummaryStringSet = new ArrayMap();
    public final List mEmailAppList = new ArrayList();
    public final AnonymousClass1 mSrResponseCallback = new PromptCallback() { // from class: com.android.systemui.statusbar.SummarizeController.1
        @Override // notification.src.com.android.systemui.PromptCallback
        public final void onComplete(String str, StringBuilder sb) {
            SummarizeController summarizeController = SummarizeController.this;
            summarizeController.getClass();
            if (summarizeController.mCommonNotifCollectionLazy.get() == null) {
                Log.d("SummarizeController", "!!!!!!!!!!!!!! SrPromptProcessor onComplete() = entry manager is null retry !!!!!!!!!!!!!!!!!!");
                Message obtain = Message.obtain();
                obtain.what = 0;
                Bundle m = AbsAdapter$1$$ExternalSyntheticOutline0.m("entry_key", str);
                m.putString("entry_summarized", sb.toString());
                obtain.setData(m);
                return;
            }
            if (summarizeController.mShadeExpansionManager.isClosed()) {
                summarizeController.updateSummarize(str, sb.toString());
                return;
            }
            MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(" keep it summarize because of panel state : ", str, "SummarizeController");
            ((ArrayMap) summarizeController.mNotificationSummaryStringSet).put(str, sb.toString());
        }

        @Override // notification.src.com.android.systemui.PromptCallback
        public final void onFailure() {
            SummarizeController.this.getClass();
        }
    };

    public SummarizeController(Context context, Lazy lazy, ShadeExpansionStateManager shadeExpansionStateManager) {
        new Handler() { // from class: com.android.systemui.statusbar.SummarizeController.2
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                if (message.what != 0) {
                    return;
                }
                Bundle data = message.getData();
                String string = data.getString("entry_key");
                String string2 = data.getString("entry_summarized");
                SummarizeController summarizeController = SummarizeController.this;
                if (summarizeController.mCommonNotifCollectionLazy.get() == null) {
                    MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("handler message  = entry manager is null retry with ", string, "SummarizeController");
                } else {
                    MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("handler message  = UPDATE_SUMMARY_RETRY with ", string, "SummarizeController");
                    summarizeController.updateSummarize(string, string2);
                }
            }
        };
        this.mContext = context;
        this.mCommonNotifCollectionLazy = lazy;
        this.mSummarizeEnabled = ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isEnableSummarize();
        this.mShadeExpansionManager = shadeExpansionStateManager;
        if (NotiRune.NOTI_SUPPORT_NOTIFICATION_SUMMARIZE_GAUSS) {
            this.mPromptProcessor = new GaussCloudPromptProcessor(context);
        } else {
            this.mPromptProcessor = new SrPromptProcessor(context);
        }
        Log.d("SummarizeController", "@@@@@@@@@@@@@@@@@@ SummarizeController !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }

    public final void updateSummarize(String str, String str2) {
        NotificationEntry entry;
        if (this.mSummarizeEnabled && (entry = ((NotifPipeline) ((CommonNotifCollection) this.mCommonNotifCollectionLazy.get())).mNotifCollection.getEntry(str)) != null) {
            entry.row.updateSummarize(str2, true);
            entry.mSummarizeDone = true;
        }
    }
}
