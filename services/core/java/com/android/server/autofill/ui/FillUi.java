package com.android.server.autofill.ui;

import android.R;
import android.app.PendingIntent;
import android.content.Context;
import android.content.IntentSender;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.RemoteException;
import android.service.autofill.Dataset;
import android.service.autofill.FillResponse;
import android.service.autofill.Flags;
import android.text.TextUtils;
import android.util.PluralsMessageFormatter;
import android.util.Slog;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.view.autofill.AutofillId;
import android.view.autofill.AutofillValue;
import android.view.autofill.IAutofillWindowPresenter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.TextView;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.UiThread;
import com.android.server.accessibility.ProxyManager$$ExternalSyntheticOutline0;
import com.android.server.alarm.GmsAlarmManager$$ExternalSyntheticOutline0;
import com.android.server.autofill.AutofillManagerService;
import com.android.server.autofill.Helper;
import com.android.server.autofill.Session;
import com.android.server.autofill.ViewState;
import com.android.server.autofill.ui.AutoFillUI;
import com.android.server.autofill.ui.FillUi;
import com.android.server.utils.Slogf;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class FillUi {
    public static final TypedValue sTempTypedValue = new TypedValue();
    public final ItemsAdapter mAdapter;
    public AnnounceFilterResult mAnnounceFilterResult;
    public final AutoFillUI.AnonymousClass1 mCallback;
    public int mContentHeight;
    public int mContentWidth;
    public final Context mContext;
    public boolean mDestroyed;
    public String mFilterText;
    public final View mFooter;
    public final boolean mFullScreen;
    public final View mHeader;
    public final ListView mListView;
    public final int mMaxInputLengthForAutofill;
    public final Point mTempPoint;
    public final int mThemeId;
    public final int mVisibleDatasetsMaxCount;
    public final AnchoredWindow mWindow;
    public final AutofillWindowPresenter mWindowPresenter;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AnchoredWindow {
        public final View mContentView;
        public final OverlayControl mOverlayControl;
        public WindowManager.LayoutParams mShowParams;
        public boolean mShowing;
        public final WindowManager mWm;

        public AnchoredWindow(View view, OverlayControl overlayControl) {
            this.mWm = (WindowManager) view.getContext().getSystemService(WindowManager.class);
            this.mContentView = view;
            this.mOverlayControl = overlayControl;
        }

        public final void hide(boolean z) {
            OverlayControl overlayControl = this.mOverlayControl;
            try {
                try {
                    if (this.mShowing) {
                        this.mWm.removeView(this.mContentView);
                        this.mShowing = false;
                    }
                } catch (IllegalStateException e) {
                    Slog.e("FillUi", "Exception hiding window ", e);
                    if (z) {
                        FillUi.this.mCallback.onDestroy();
                    }
                }
            } finally {
                overlayControl.setOverlayAllowed(true);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AnnounceFilterResult implements Runnable {
        public AnnounceFilterResult() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            String format;
            int count = FillUi.this.mListView.getAdapter().getCount();
            if (count <= 0) {
                format = FillUi.this.mContext.getString(R.string.config_customAdbWifiNetworkConfirmationSecondaryUserComponent);
            } else {
                HashMap hashMap = new HashMap();
                hashMap.put("count", Integer.valueOf(count));
                format = PluralsMessageFormatter.format(FillUi.this.mContext.getResources(), hashMap, R.string.config_customCountryDetector);
            }
            FillUi.this.mListView.announceForAccessibility(format);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AutofillWindowPresenter extends IAutofillWindowPresenter.Stub {
        public AutofillWindowPresenter() {
        }

        public final void hide(Rect rect) {
            UiThread.getHandler().post(new Runnable() { // from class: com.android.server.autofill.ui.FillUi$AutofillWindowPresenter$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    FillUi.AnchoredWindow anchoredWindow = FillUi.this.mWindow;
                    if (anchoredWindow != null) {
                        anchoredWindow.hide(true);
                    }
                }
            });
        }

        public final void show(final WindowManager.LayoutParams layoutParams, Rect rect, boolean z, int i) {
            if (Helper.sVerbose) {
                StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m("AutofillWindowPresenter.show(): fit=", ", params=", z);
                StringBuilder sb = new StringBuilder(25);
                layoutParams.dumpDimensions(sb);
                m.append(sb.toString());
                Slog.v("FillUi", m.toString());
            }
            UiThread.getHandler().post(new Runnable() { // from class: com.android.server.autofill.ui.FillUi$AutofillWindowPresenter$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    FillUi.AutofillWindowPresenter autofillWindowPresenter = FillUi.AutofillWindowPresenter.this;
                    WindowManager.LayoutParams layoutParams2 = layoutParams;
                    FillUi.AnchoredWindow anchoredWindow = FillUi.this.mWindow;
                    if (anchoredWindow != null) {
                        FillUi fillUi = FillUi.this;
                        anchoredWindow.mShowParams = layoutParams2;
                        if (Helper.sVerbose) {
                            StringBuilder sb2 = new StringBuilder("show(): showing=");
                            sb2.append(anchoredWindow.mShowing);
                            sb2.append(", params=");
                            StringBuilder sb3 = new StringBuilder(25);
                            layoutParams2.dumpDimensions(sb3);
                            sb2.append(sb3.toString());
                            Slog.v("FillUi", sb2.toString());
                        }
                        try {
                            layoutParams2.packageName = "android";
                            layoutParams2.setTitle("Autofill UI");
                            if (anchoredWindow.mShowing) {
                                anchoredWindow.mWm.updateViewLayout(anchoredWindow.mContentView, layoutParams2);
                                return;
                            }
                            layoutParams2.accessibilityTitle = anchoredWindow.mContentView.getContext().getString(R.string.config_customAdbWifiNetworkConfirmationComponent);
                            anchoredWindow.mWm.addView(anchoredWindow.mContentView, layoutParams2);
                            int i2 = 0;
                            anchoredWindow.mOverlayControl.setOverlayAllowed(false);
                            anchoredWindow.mShowing = true;
                            FillUi.ItemsAdapter itemsAdapter = fillUi.mAdapter;
                            if (itemsAdapter != null) {
                                i2 = itemsAdapter.getCount();
                            }
                            AutoFillUI.AutoFillUiCallback autoFillUiCallback = AutoFillUI.this.mCallback;
                            if (autoFillUiCallback != null) {
                                ((Session) autoFillUiCallback).onShown(1, i2);
                            }
                        } catch (WindowManager.BadTokenException unused) {
                            if (Helper.sDebug) {
                                Slog.d("FillUi", "Filed with with token " + layoutParams2.token + " gone.");
                            }
                            fillUi.mCallback.onDestroy();
                        } catch (IllegalStateException e) {
                            Slog.wtf("FillUi", "Exception showing window " + layoutParams2, e);
                            fillUi.mCallback.onDestroy();
                        }
                    }
                }
            });
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ItemsAdapter extends BaseAdapter implements Filterable {
        public final List mAllItems;
        public final List mFilteredItems;

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        /* renamed from: com.android.server.autofill.ui.FillUi$ItemsAdapter$1, reason: invalid class name */
        public final class AnonymousClass1 extends Filter {
            public AnonymousClass1() {
            }

            @Override // android.widget.Filter
            public final Filter.FilterResults performFiltering(final CharSequence charSequence) {
                List list = (List) ItemsAdapter.this.mAllItems.stream().filter(new Predicate() { // from class: com.android.server.autofill.ui.FillUi$ItemsAdapter$1$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        CharSequence charSequence2 = charSequence;
                        FillUi.ViewItem viewItem = (FillUi.ViewItem) obj;
                        viewItem.getClass();
                        if (TextUtils.isEmpty(charSequence2)) {
                            return true;
                        }
                        if (viewItem.filterable) {
                            String lowerCase = charSequence2.toString().toLowerCase();
                            Pattern pattern = viewItem.filter;
                            if (pattern != null) {
                                return pattern.matcher(lowerCase).matches();
                            }
                            String str = viewItem.value;
                            if (str != null) {
                                return str.toLowerCase().startsWith(lowerCase);
                            }
                            if (viewItem.dataset.getAuthentication() == null) {
                                return true;
                            }
                        }
                        return false;
                    }
                }).collect(Collectors.toList());
                Filter.FilterResults filterResults = new Filter.FilterResults();
                filterResults.values = list;
                filterResults.count = list.size();
                return filterResults;
            }

            @Override // android.widget.Filter
            public final void publishResults(CharSequence charSequence, Filter.FilterResults filterResults) {
                int size = ((ArrayList) ItemsAdapter.this.mFilteredItems).size();
                ((ArrayList) ItemsAdapter.this.mFilteredItems).clear();
                if (filterResults.count > 0) {
                    ((ArrayList) ItemsAdapter.this.mFilteredItems).addAll((List) filterResults.values);
                }
                if (size != ((ArrayList) ItemsAdapter.this.mFilteredItems).size()) {
                    FillUi fillUi = FillUi.this;
                    if (AccessibilityManager.getInstance(fillUi.mContext).isEnabled()) {
                        if (fillUi.mAnnounceFilterResult == null) {
                            fillUi.mAnnounceFilterResult = fillUi.new AnnounceFilterResult();
                        }
                        AnnounceFilterResult announceFilterResult = fillUi.mAnnounceFilterResult;
                        FillUi.this.mListView.removeCallbacks(announceFilterResult);
                        FillUi.this.mListView.postDelayed(announceFilterResult, 1000L);
                    }
                }
                ItemsAdapter.this.notifyDataSetChanged();
            }
        }

        public ItemsAdapter(List list) {
            ArrayList arrayList = new ArrayList();
            this.mFilteredItems = arrayList;
            this.mAllItems = Collections.unmodifiableList(new ArrayList(list));
            arrayList.addAll(list);
        }

        @Override // android.widget.Adapter
        public final int getCount() {
            return ((ArrayList) this.mFilteredItems).size();
        }

        @Override // android.widget.Filterable
        public final Filter getFilter() {
            return new AnonymousClass1();
        }

        @Override // android.widget.Adapter
        public final Object getItem(int i) {
            return (ViewItem) ((ArrayList) this.mFilteredItems).get(i);
        }

        @Override // android.widget.Adapter
        public final long getItemId(int i) {
            return i;
        }

        @Override // android.widget.Adapter
        public final View getView(int i, View view, ViewGroup viewGroup) {
            return ((ViewItem) ((ArrayList) this.mFilteredItems).get(i)).view;
        }

        public final String toString() {
            return "ItemsAdapter: [all=" + this.mAllItems + ", filtered=" + this.mFilteredItems + "]";
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ViewItem {
        public final Dataset dataset;
        public final Pattern filter;
        public final boolean filterable;
        public final String value;
        public final View view;

        public ViewItem(Dataset dataset, Pattern pattern, boolean z, String str, View view) {
            this.dataset = dataset;
            this.value = str;
            this.view = view;
            this.filter = pattern;
            this.filterable = z;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("ViewItem:[view=");
            sb.append(this.view.getAutofillId());
            Dataset dataset = this.dataset;
            String id = dataset == null ? null : dataset.getId();
            if (id != null) {
                sb.append(", dataset=");
                sb.append(id);
            }
            String str = this.value;
            if (str != null) {
                sb.append(", value=");
                sb.append(str.length());
                sb.append("_chars");
            }
            if (this.filterable) {
                sb.append(", filterable");
            }
            if (this.filter != null) {
                sb.append(", filter=");
                sb.append(this.filter.pattern().length());
                sb.append("_chars");
            }
            sb.append(']');
            return sb.toString();
        }
    }

    public FillUi(Context context, final FillResponse fillResponse, AutofillId autofillId, String str, OverlayControl overlayControl, CharSequence charSequence, Drawable drawable, boolean z, int i, AutoFillUI.AnonymousClass1 anonymousClass1) {
        boolean hasSystemFeature;
        FillUi$$ExternalSyntheticLambda5 fillUi$$ExternalSyntheticLambda5;
        int i2;
        Pattern pattern;
        boolean z2;
        String str2;
        Point point = new Point();
        this.mTempPoint = point;
        this.mWindowPresenter = new AutofillWindowPresenter();
        if (Helper.sVerbose) {
            Slogf.v("FillUi", "nightMode: %b displayId: %d", Boolean.valueOf(z), Integer.valueOf(context.getDisplayId()));
        }
        int i3 = z ? R.style.Theme.Holo.SearchBar : R.style.Theme.Material.Dialog.NoFrame;
        this.mThemeId = i3;
        this.mCallback = anonymousClass1;
        if (Helper.sFullScreenMode != null) {
            if (Helper.sVerbose) {
                Slog.v("FillUi", "forcing full-screen mode to " + Helper.sFullScreenMode);
            }
            hasSystemFeature = Helper.sFullScreenMode.booleanValue();
        } else {
            hasSystemFeature = context.getPackageManager().hasSystemFeature("android.software.leanback");
        }
        this.mFullScreen = hasSystemFeature;
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, i3);
        this.mContext = contextThemeWrapper;
        this.mMaxInputLengthForAutofill = i;
        LayoutInflater from = LayoutInflater.from(contextThemeWrapper);
        RemoteViews sanitizeRemoteView = Helper.sanitizeRemoteView(fillResponse.getHeader());
        RemoteViews sanitizeRemoteView2 = Helper.sanitizeRemoteView(fillResponse.getFooter());
        ViewGroup viewGroup = hasSystemFeature ? (ViewGroup) from.inflate(R.layout.car_alert_dialog_title, (ViewGroup) null) : (sanitizeRemoteView == null && sanitizeRemoteView2 == null) ? (ViewGroup) from.inflate(R.layout.car_alert_dialog_button_bar, (ViewGroup) null) : (ViewGroup) from.inflate(R.layout.car_preference, (ViewGroup) null);
        viewGroup.setClipToOutline(true);
        TextView textView = (TextView) viewGroup.findViewById(R.id.autofill_save_yes);
        if (textView != null) {
            textView.setText(contextThemeWrapper.getString(R.string.config_defaultDndAccessPackages, charSequence));
        }
        ImageView imageView = (ImageView) viewGroup.findViewById(R.id.autofill_save_icon);
        if (imageView != null) {
            imageView.setImageDrawable(drawable);
        }
        if (hasSystemFeature) {
            contextThemeWrapper.getDisplayNoVerify().getSize(point);
            this.mContentWidth = -1;
            this.mContentHeight = point.y / 2;
            if (Helper.sVerbose) {
                StringBuilder sb = new StringBuilder("initialized fillscreen LayoutParams ");
                sb.append(this.mContentWidth);
                sb.append(",");
                GmsAlarmManager$$ExternalSyntheticOutline0.m(sb, this.mContentHeight, "FillUi");
            }
        }
        viewGroup.addOnUnhandledKeyEventListener(new View.OnUnhandledKeyEventListener() { // from class: com.android.server.autofill.ui.FillUi$$ExternalSyntheticLambda1
            @Override // android.view.View.OnUnhandledKeyEventListener
            public final boolean onUnhandledKeyEvent(View view, KeyEvent keyEvent) {
                FillUi fillUi = FillUi.this;
                fillUi.getClass();
                int keyCode = keyEvent.getKeyCode();
                if (keyCode != 4 && keyCode != 66 && keyCode != 111) {
                    switch (keyCode) {
                        case 19:
                        case 20:
                        case 21:
                        case 22:
                        case 23:
                            break;
                        default:
                            AutoFillUI.AnonymousClass1 anonymousClass12 = fillUi.mCallback;
                            AutoFillUI.AutoFillUiCallback autoFillUiCallback = AutoFillUI.this.mCallback;
                            if (autoFillUiCallback != null) {
                                AutofillId autofillId2 = (AutofillId) anonymousClass12.val$focusedId;
                                Session session = (Session) autoFillUiCallback;
                                synchronized (session.mLock) {
                                    try {
                                    } catch (RemoteException e) {
                                        Slog.e("AutofillSession", "Error requesting to dispatch unhandled key", e);
                                    } finally {
                                    }
                                    if (session.mDestroyed) {
                                        Slog.w("AutofillSession", "Call to Session#dispatchUnhandledKey() rejected - session: " + autofillId2 + " destroyed");
                                    } else if (autofillId2.equals(session.mCurrentViewId)) {
                                        session.mClient.dispatchUnhandledKey(session.id, autofillId2, keyEvent);
                                    } else {
                                        Slog.w("AutofillSession", "Do not dispatch unhandled key on " + autofillId2 + " as it is not the current view (" + session.mCurrentViewId + ") anymore");
                                    }
                                }
                            }
                            return true;
                    }
                }
                return false;
            }
        });
        if (AutofillManagerService.getVisibleDatasetsMaxCount() > 0) {
            int visibleDatasetsMaxCount = AutofillManagerService.getVisibleDatasetsMaxCount();
            this.mVisibleDatasetsMaxCount = visibleDatasetsMaxCount;
            if (Helper.sVerbose) {
                ProxyManager$$ExternalSyntheticOutline0.m(visibleDatasetsMaxCount, "overriding maximum visible datasets to ", "FillUi");
            }
        } else if (!Flags.autofillCredmanIntegration() || (fillResponse.getFlags() & 8) == 0) {
            this.mVisibleDatasetsMaxCount = contextThemeWrapper.getResources().getInteger(R.integer.autofill_max_visible_datasets);
        } else {
            this.mVisibleDatasetsMaxCount = 5;
        }
        RemoteViews.InteractionHandler interactionHandler = new RemoteViews.InteractionHandler() { // from class: com.android.server.autofill.ui.FillUi$$ExternalSyntheticLambda2
            public final boolean onInteraction(View view, PendingIntent pendingIntent, RemoteViews.RemoteResponse remoteResponse) {
                FillUi fillUi = FillUi.this;
                fillUi.getClass();
                if (pendingIntent == null) {
                    return true;
                }
                IntentSender intentSender = pendingIntent.getIntentSender();
                AutoFillUI.AutoFillUiCallback autoFillUiCallback = AutoFillUI.this.mCallback;
                if (autoFillUiCallback == null) {
                    return true;
                }
                ((Session) autoFillUiCallback).startIntentSender(intentSender, null);
                return true;
            }
        };
        if (fillResponse.getAuthentication() != null) {
            this.mHeader = null;
            this.mListView = null;
            this.mFooter = null;
            this.mAdapter = null;
            ViewGroup viewGroup2 = (ViewGroup) viewGroup.findViewById(R.id.autofill_save_title);
            try {
                if (Helper.sanitizeRemoteView(fillResponse.getPresentation()) == null) {
                    throw new RuntimeException("Permission error accessing RemoteView");
                }
                View applyWithTheme = fillResponse.getPresentation().applyWithTheme(contextThemeWrapper, viewGroup, interactionHandler, i3);
                viewGroup2.addView(applyWithTheme);
                viewGroup2.setFocusable(true);
                viewGroup2.setOnClickListener(new View.OnClickListener() { // from class: com.android.server.autofill.ui.FillUi$$ExternalSyntheticLambda3
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        FillUi fillUi = FillUi.this;
                        FillResponse fillResponse2 = fillResponse;
                        AutoFillUI.AnonymousClass1 anonymousClass12 = fillUi.mCallback;
                        anonymousClass12.val$log.setType(3);
                        AutoFillUI autoFillUI = AutoFillUI.this;
                        autoFillUI.hideFillUiUiThread(anonymousClass12.val$callback, true);
                        AutoFillUI.AutoFillUiCallback autoFillUiCallback = autoFillUI.mCallback;
                        if (autoFillUiCallback != null) {
                            ((Session) autoFillUiCallback).authenticate(fillResponse2.getRequestId(), fillResponse2.getAuthentication(), fillResponse2.getClientState(), 1);
                        }
                    }
                });
                if (!hasSystemFeature) {
                    resolveMaxWindowSize(contextThemeWrapper, point);
                    applyWithTheme.getLayoutParams().width = hasSystemFeature ? point.x : -2;
                    applyWithTheme.getLayoutParams().height = -2;
                    viewGroup.measure(View.MeasureSpec.makeMeasureSpec(point.x, Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(point.y, Integer.MIN_VALUE));
                    this.mContentWidth = applyWithTheme.getMeasuredWidth();
                    this.mContentHeight = applyWithTheme.getMeasuredHeight();
                }
                this.mWindow = new AnchoredWindow(viewGroup, overlayControl);
                requestShowFillUi();
                return;
            } catch (RuntimeException e) {
                anonymousClass1.val$log.setType(5);
                AutoFillUI.this.hideFillUiUiThread(anonymousClass1.val$callback, true);
                Slog.e("FillUi", "Error inflating remote views", e);
                this.mWindow = null;
                return;
            }
        }
        int size = fillResponse.getDatasets().size();
        if (Helper.sVerbose) {
            GmsAlarmManager$$ExternalSyntheticOutline0.m(BatteryService$$ExternalSyntheticOutline0.m(size, "Number datasets: ", " max visible: "), this.mVisibleDatasetsMaxCount, "FillUi");
        }
        if (sanitizeRemoteView != null) {
            fillUi$$ExternalSyntheticLambda5 = new FillUi$$ExternalSyntheticLambda5();
            View applyWithTheme2 = sanitizeRemoteView.applyWithTheme(contextThemeWrapper, null, fillUi$$ExternalSyntheticLambda5, i3);
            this.mHeader = applyWithTheme2;
            LinearLayout linearLayout = (LinearLayout) viewGroup.findViewById(R.id.autofill_save_custom_subtitle);
            applyCancelAction(applyWithTheme2, fillResponse.getCancelIds());
            if (Helper.sVerbose) {
                Slog.v("FillUi", "adding header");
            }
            linearLayout.addView(applyWithTheme2);
            linearLayout.setVisibility(0);
        } else {
            this.mHeader = null;
            fillUi$$ExternalSyntheticLambda5 = null;
        }
        if (sanitizeRemoteView2 != null) {
            LinearLayout linearLayout2 = (LinearLayout) viewGroup.findViewById(R.id.autofill_save_button_bar);
            if (linearLayout2 != null) {
                View applyWithTheme3 = sanitizeRemoteView2.applyWithTheme(contextThemeWrapper, null, fillUi$$ExternalSyntheticLambda5 == null ? new FillUi$$ExternalSyntheticLambda5() : fillUi$$ExternalSyntheticLambda5, i3);
                this.mFooter = applyWithTheme3;
                applyCancelAction(applyWithTheme3, fillResponse.getCancelIds());
                if (Helper.sVerbose) {
                    Slog.v("FillUi", "adding footer");
                }
                linearLayout2.addView(applyWithTheme3);
                linearLayout2.setVisibility(0);
            } else {
                this.mFooter = null;
            }
        } else {
            this.mFooter = null;
        }
        ArrayList arrayList = new ArrayList(size);
        int i4 = 0;
        while (i4 < size) {
            Dataset dataset = (Dataset) fillResponse.getDatasets().get(i4);
            int indexOf = dataset.getFieldIds().indexOf(autofillId);
            if (indexOf >= 0) {
                RemoteViews sanitizeRemoteView3 = Helper.sanitizeRemoteView(dataset.getFieldPresentation(indexOf));
                if (sanitizeRemoteView3 == null) {
                    Slog.w("FillUi", "not displaying UI on field " + autofillId + " because service didn't provide a presentation for it on " + dataset);
                } else {
                    try {
                        if (Helper.sVerbose) {
                            Slog.v("FillUi", "setting remote view for " + autofillId);
                        }
                        i2 = size;
                    } catch (RuntimeException e2) {
                        e = e2;
                        i2 = size;
                    }
                    try {
                        View applyWithTheme4 = sanitizeRemoteView3.applyWithTheme(this.mContext, null, interactionHandler, this.mThemeId);
                        Dataset.DatasetFieldFilter filter = dataset.getFilter(indexOf);
                        if (filter == null) {
                            AutofillValue autofillValue = (AutofillValue) dataset.getFieldValues().get(indexOf);
                            str2 = (autofillValue == null || !autofillValue.isText()) ? null : autofillValue.getTextValue().toString().toLowerCase();
                            pattern = null;
                            z2 = true;
                        } else {
                            Pattern pattern2 = filter.pattern;
                            if (pattern2 == null) {
                                if (Helper.sVerbose) {
                                    Slog.v("FillUi", "Explicitly disabling filter at id " + autofillId + " for dataset #" + indexOf);
                                }
                                pattern = pattern2;
                                z2 = false;
                            } else {
                                pattern = pattern2;
                                z2 = true;
                            }
                            str2 = null;
                        }
                        applyCancelAction(applyWithTheme4, fillResponse.getCancelIds());
                        arrayList.add(new ViewItem(dataset, pattern, z2, str2, applyWithTheme4));
                    } catch (RuntimeException e3) {
                        e = e3;
                        Slog.e("FillUi", "Error inflating remote views", e);
                        i4++;
                        size = i2;
                    }
                    i4++;
                    size = i2;
                }
            }
            i2 = size;
            i4++;
            size = i2;
        }
        ItemsAdapter itemsAdapter = new ItemsAdapter(arrayList);
        this.mAdapter = itemsAdapter;
        ListView listView = (ListView) viewGroup.findViewById(R.id.autofill_save_no);
        this.mListView = listView;
        listView.setAdapter((ListAdapter) itemsAdapter);
        listView.setVisibility(0);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.android.server.autofill.ui.FillUi$$ExternalSyntheticLambda4
            @Override // android.widget.AdapterView.OnItemClickListener
            public final void onItemClick(AdapterView adapterView, View view, int i5, long j) {
                FillUi fillUi = FillUi.this;
                Dataset dataset2 = ((FillUi.ViewItem) ((ArrayList) fillUi.mAdapter.mFilteredItems).get(i5)).dataset;
                AutoFillUI.AnonymousClass1 anonymousClass12 = fillUi.mCallback;
                anonymousClass12.val$log.setType(4);
                AutoFillUI autoFillUI = AutoFillUI.this;
                autoFillUI.hideFillUiUiThread(anonymousClass12.val$callback, true);
                if (autoFillUI.mCallback != null) {
                    int indexOf2 = ((FillResponse) anonymousClass12.val$response).getDatasets().indexOf(dataset2);
                    ((Session) autoFillUI.mCallback).fill(((FillResponse) anonymousClass12.val$response).getRequestId(), indexOf2, dataset2, 1);
                }
            }
        });
        if (str == null) {
            this.mFilterText = null;
        } else {
            this.mFilterText = str.toLowerCase();
        }
        itemsAdapter.new AnonymousClass1().filter(this.mFilterText, new FillUi$$ExternalSyntheticLambda0(this, itemsAdapter.getCount()));
        this.mWindow = new AnchoredWindow(viewGroup, overlayControl);
    }

    public static void resolveMaxWindowSize(Context context, Point point) {
        context.getDisplayNoVerify().getSize(point);
        TypedValue typedValue = sTempTypedValue;
        context.getTheme().resolveAttribute(R.^attr-private.autofillDatasetPickerMaxWidth, typedValue, true);
        int i = point.x;
        point.x = (int) typedValue.getFraction(i, i);
        context.getTheme().resolveAttribute(R.^attr-private.autofillDatasetPickerMaxHeight, typedValue, true);
        int i2 = point.y;
        point.y = (int) typedValue.getFraction(i2, i2);
    }

    public final void applyCancelAction(View view, int[] iArr) {
        if (iArr == null) {
            return;
        }
        if (Helper.sDebug) {
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("fill UI has "), iArr.length, " actions", "FillUi");
        }
        if (!(view instanceof ViewGroup)) {
            Slog.w("FillUi", "cannot apply actions because fill UI root is not a ViewGroup: " + view);
            return;
        }
        ViewGroup viewGroup = (ViewGroup) view;
        for (int i : iArr) {
            View findViewById = viewGroup.findViewById(i);
            if (findViewById == null) {
                Slog.w("FillUi", "Ignoring cancel action for view " + i + " because it's not on " + viewGroup);
            } else {
                findViewById.setOnClickListener(new View.OnClickListener() { // from class: com.android.server.autofill.ui.FillUi$$ExternalSyntheticLambda6
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        FillUi fillUi = FillUi.this;
                        if (Helper.sVerbose) {
                            fillUi.getClass();
                            Slog.v("FillUi", " Cancelling session after " + view2 + " clicked");
                        }
                        AutoFillUI.AutoFillUiCallback autoFillUiCallback = AutoFillUI.this.mCallback;
                        if (autoFillUiCallback != null) {
                            Session session = (Session) autoFillUiCallback;
                            synchronized (session.mLock) {
                                session.removeFromServiceLocked();
                            }
                        }
                    }
                });
            }
        }
    }

    public final void requestShowFillUi() {
        AutoFillUI.AnonymousClass1 anonymousClass1 = this.mCallback;
        int i = this.mContentWidth;
        int i2 = this.mContentHeight;
        AutofillWindowPresenter autofillWindowPresenter = this.mWindowPresenter;
        AutoFillUI.AutoFillUiCallback autoFillUiCallback = AutoFillUI.this.mCallback;
        if (autoFillUiCallback != null) {
            AutofillId autofillId = (AutofillId) anonymousClass1.val$focusedId;
            Session session = (Session) autoFillUiCallback;
            synchronized (session.mLock) {
                try {
                } catch (RemoteException e) {
                    Slog.e("AutofillSession", "Error requesting to show fill UI", e);
                } finally {
                }
                if (session.mDestroyed) {
                    Slog.w("AutofillSession", "Call to Session#requestShowFillUi() rejected - session: " + autofillId + " destroyed");
                    return;
                }
                if (autofillId.equals(session.mCurrentViewId)) {
                    session.mClient.requestShowFillUi(session.id, autofillId, i, i2, ((ViewState) session.mViewStates.get(autofillId)).mVirtualBounds, autofillWindowPresenter);
                } else if (Helper.sDebug) {
                    Slog.d("AutofillSession", "Do not show full UI on " + autofillId + " as it is not the current view (" + session.mCurrentViewId + ") anymore");
                }
            }
        }
    }

    public final boolean updateHeight(View view, Point point) {
        int min = Math.min(view.getMeasuredHeight(), point.y);
        int i = this.mContentHeight;
        int i2 = min + i;
        if (i2 == i) {
            return false;
        }
        this.mContentHeight = i2;
        return true;
    }

    public final boolean updateWidth(View view, Point point) {
        int max = Math.max(this.mContentWidth, Math.min(view.getMeasuredWidth(), point.x));
        if (max == this.mContentWidth) {
            return false;
        }
        this.mContentWidth = max;
        return true;
    }
}
