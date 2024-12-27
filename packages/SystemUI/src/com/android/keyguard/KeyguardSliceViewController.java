package com.android.keyguard;

import android.app.PendingIntent;
import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Trace;
import android.util.Log;
import android.view.Display;
import android.view.View;
import androidx.collection.ArraySet;
import androidx.lifecycle.Observer;
import androidx.slice.ArrayUtils;
import androidx.slice.Slice;
import androidx.slice.SliceViewManagerWrapper;
import androidx.slice.widget.ListContent;
import androidx.slice.widget.RowContent;
import androidx.slice.widget.SliceLiveData;
import com.android.keyguard.KeyguardSliceView;
import com.android.systemui.Dumpable;
import com.android.systemui.Flags;
import com.android.systemui.R;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.KeyguardSliceProvider;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.ViewController;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class KeyguardSliceViewController extends ViewController implements Dumpable {
    public final ActivityStarter mActivityStarter;
    public final Handler mBgHandler;
    public Map mClickActions;
    public final ConfigurationController mConfigurationController;
    public final AnonymousClass1 mConfigurationListener;
    public int mDisplayId;
    public final DisplayTracker mDisplayTracker;
    public final DumpManager mDumpManager;
    public final Handler mHandler;
    public Uri mKeyguardSliceUri;
    public SliceLiveData.SliceLiveDataImpl mLiveData;
    public final AnonymousClass2 mObserver;
    public Slice mSlice;
    public final KeyguardSliceViewController$$ExternalSyntheticLambda0 mTunable;
    public final TunerService mTunerService;

    /* renamed from: com.android.keyguard.KeyguardSliceViewController$2, reason: invalid class name */
    public final class AnonymousClass2 implements Observer {
        public AnonymousClass2() {
        }

        @Override // androidx.lifecycle.Observer
        public final void onChanged(Object obj) {
            Slice slice = (Slice) obj;
            KeyguardSliceViewController keyguardSliceViewController = KeyguardSliceViewController.this;
            keyguardSliceViewController.mSlice = slice;
            keyguardSliceViewController.showSlice(slice);
        }
    }

    public static void $r8$lambda$lUy0jQLhlGlsmvVdjvilmBh6_ps(KeyguardSliceViewController keyguardSliceViewController, String str) {
        boolean z;
        keyguardSliceViewController.getClass();
        if (str == null) {
            str = "content://com.android.systemui.keyguard/main";
        }
        SliceLiveData.SliceLiveDataImpl sliceLiveDataImpl = keyguardSliceViewController.mLiveData;
        AnonymousClass2 anonymousClass2 = keyguardSliceViewController.mObserver;
        if (sliceLiveDataImpl == null || !sliceLiveDataImpl.hasActiveObservers()) {
            z = false;
        } else {
            keyguardSliceViewController.mLiveData.removeObserver(anonymousClass2);
            z = true;
        }
        keyguardSliceViewController.mKeyguardSliceUri = Uri.parse(str);
        Context context = ((KeyguardSliceView) keyguardSliceViewController.mView).getContext();
        Uri uri = keyguardSliceViewController.mKeyguardSliceUri;
        ArraySet arraySet = SliceLiveData.SUPPORTED_SPECS;
        SliceLiveData.SliceLiveDataImpl sliceLiveDataImpl2 = new SliceLiveData.SliceLiveDataImpl(context.getApplicationContext(), uri, (SliceLiveData.OnErrorListener) null);
        keyguardSliceViewController.mLiveData = sliceLiveDataImpl2;
        if (z) {
            sliceLiveDataImpl2.observeForever(anonymousClass2);
        }
    }

    /* JADX WARN: Type inference failed for: r3v1, types: [com.android.keyguard.KeyguardSliceViewController$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.android.keyguard.KeyguardSliceViewController$1] */
    public KeyguardSliceViewController(Handler handler, Handler handler2, KeyguardSliceView keyguardSliceView, ActivityStarter activityStarter, ConfigurationController configurationController, TunerService tunerService, DumpManager dumpManager, DisplayTracker displayTracker) {
        super(keyguardSliceView);
        this.mTunable = new TunerService.Tunable() { // from class: com.android.keyguard.KeyguardSliceViewController$$ExternalSyntheticLambda0
            @Override // com.android.systemui.tuner.TunerService.Tunable
            public final void onTuningChanged(String str, String str2) {
                KeyguardSliceViewController.$r8$lambda$lUy0jQLhlGlsmvVdjvilmBh6_ps(KeyguardSliceViewController.this, str2);
            }
        };
        this.mConfigurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.keyguard.KeyguardSliceViewController.1
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onDensityOrFontScaleChanged() {
                ((KeyguardSliceView) ((ViewController) KeyguardSliceViewController.this).mView).onDensityOrFontScaleChanged();
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onThemeChanged() {
                KeyguardSliceView keyguardSliceView2 = (KeyguardSliceView) ((ViewController) KeyguardSliceViewController.this).mView;
                for (int i = 0; i < keyguardSliceView2.mRow.getChildCount(); i++) {
                    View childAt = keyguardSliceView2.mRow.getChildAt(i);
                    if (childAt instanceof KeyguardSliceView.KeyguardSliceTextView) {
                        ((KeyguardSliceView.KeyguardSliceTextView) childAt).setTextAppearance(R.style.TextAppearance_Keyguard_Secondary);
                    }
                }
            }
        };
        this.mObserver = new AnonymousClass2();
        new View.OnClickListener() { // from class: com.android.keyguard.KeyguardSliceViewController.3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ActivityStarter activityStarter2;
                PendingIntent pendingIntent = (PendingIntent) ((HashMap) KeyguardSliceViewController.this.mClickActions).get(view);
                if (pendingIntent == null || (activityStarter2 = KeyguardSliceViewController.this.mActivityStarter) == null) {
                    return;
                }
                activityStarter2.startPendingIntentDismissingKeyguard(pendingIntent);
            }
        };
        this.mHandler = handler;
        this.mBgHandler = handler2;
        this.mActivityStarter = activityStarter;
        this.mConfigurationController = configurationController;
        this.mTunerService = tunerService;
        this.mDumpManager = dumpManager;
        this.mDisplayTracker = displayTracker;
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("  mSlice: " + this.mSlice);
        printWriter.println("  mClickActions: " + this.mClickActions);
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
        SliceLiveData.SliceLiveDataImpl sliceLiveDataImpl;
        Display display = ((KeyguardSliceView) this.mView).getDisplay();
        if (display != null) {
            this.mDisplayId = display.getDisplayId();
        }
        this.mTunerService.addTunable(this.mTunable, "keyguard_slice_uri");
        int i = this.mDisplayId;
        this.mDisplayTracker.getClass();
        if (i == 0 && (sliceLiveDataImpl = this.mLiveData) != null) {
            sliceLiveDataImpl.observeForever(this.mObserver);
        }
        ((ConfigurationControllerImpl) this.mConfigurationController).addCallback(this.mConfigurationListener);
        this.mDumpManager.registerNormalDumpable("KeyguardSliceViewCtrl@" + Integer.toHexString(hashCode()), this);
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
        int i = this.mDisplayId;
        this.mDisplayTracker.getClass();
        if (i == 0) {
            this.mLiveData.removeObserver(this.mObserver);
        }
        this.mTunerService.removeTunable(this.mTunable);
        ((ConfigurationControllerImpl) this.mConfigurationController).removeCallback(this.mConfigurationListener);
        this.mDumpManager.unregisterDumpable("KeyguardSliceViewCtrl@" + Integer.toHexString(hashCode()));
    }

    public final void refresh() {
        Slice bindSlice;
        Trace.beginSection("KeyguardSliceViewController#refresh");
        try {
            if ("content://com.android.systemui.keyguard/main".equals(this.mKeyguardSliceUri.toString())) {
                KeyguardSliceProvider keyguardSliceProvider = KeyguardSliceProvider.sInstance;
                if (keyguardSliceProvider != null) {
                    Flags.FEATURE_FLAGS.getClass();
                    this.mBgHandler.post(new KeyguardSliceViewController$$ExternalSyntheticLambda1(this, keyguardSliceProvider, 0));
                    return;
                } else {
                    Log.w("KeyguardSliceViewCtrl", "Keyguard slice not bound yet?");
                    bindSlice = null;
                }
            } else {
                bindSlice = new SliceViewManagerWrapper(((KeyguardSliceView) this.mView).getContext()).bindSlice(this.mKeyguardSliceUri);
            }
            this.mObserver.onChanged(bindSlice);
        } finally {
            Trace.endSection();
        }
    }

    public final void showSlice(Slice slice) {
        Trace.beginSection("KeyguardSliceViewController#showSlice");
        boolean z = false;
        if (slice == null) {
            KeyguardSliceView keyguardSliceView = (KeyguardSliceView) this.mView;
            keyguardSliceView.mTitle.setVisibility(8);
            keyguardSliceView.mRow.setVisibility(8);
            keyguardSliceView.mHasHeader = false;
            Trace.endSection();
            return;
        }
        ListContent listContent = new ListContent(slice);
        RowContent rowContent = listContent.mHeaderContent;
        if (rowContent != null && !ArrayUtils.contains(rowContent.mSliceItem.mHints, "list_item")) {
            z = true;
        }
        List list = (List) listContent.mRowItems.stream().filter(new KeyguardSliceViewController$$ExternalSyntheticLambda3()).collect(Collectors.toList());
        KeyguardSliceView keyguardSliceView2 = (KeyguardSliceView) this.mView;
        if (!z) {
            rowContent = null;
        }
        this.mClickActions = keyguardSliceView2.showSlice(rowContent, list);
        Trace.endSection();
    }
}
