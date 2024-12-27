package com.android.server.autofill.ui;

import android.R;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.service.autofill.Dataset;
import android.service.autofill.FillResponse;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.PluralsMessageFormatter;
import android.util.Slog;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.view.autofill.AutofillId;
import android.view.autofill.AutofillValue;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.TextView;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.ProxyManager$$ExternalSyntheticOutline0;
import com.android.server.autofill.AutofillManagerService;
import com.android.server.autofill.Helper;
import com.android.server.autofill.PresentationStatsEventLogger;
import com.android.server.autofill.PresentationStatsEventLogger$$ExternalSyntheticLambda0;
import com.android.server.autofill.Session;
import com.android.server.autofill.ui.AutoFillUI;
import com.android.server.autofill.ui.DialogFillUi;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class DialogFillUi {
    public final ItemsAdapter mAdapter;
    public AnnounceFilterResult mAnnounceFilterResult;
    public final AutoFillUI.AnonymousClass3 mCallback;
    public final ComponentName mComponentName;
    public final Context mContext;
    public boolean mDestroyed;
    public final Dialog mDialog;
    public final String mFilterText;
    public final ListView mListView;
    public final OverlayControl mOverlayControl;
    public final String mServicePackageName;
    public final int mThemeId;
    public final int mVisibleDatasetsMaxCount;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AnnounceFilterResult implements Runnable {
        public AnnounceFilterResult() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            String format;
            int count = DialogFillUi.this.mListView.getAdapter().getCount();
            if (count <= 0) {
                format = DialogFillUi.this.mContext.getString(R.string.config_customAdbWifiNetworkConfirmationSecondaryUserComponent);
            } else {
                HashMap hashMap = new HashMap();
                hashMap.put("count", Integer.valueOf(count));
                format = PluralsMessageFormatter.format(DialogFillUi.this.mContext.getResources(), hashMap, R.string.config_customCountryDetector);
            }
            DialogFillUi.this.mListView.announceForAccessibility(format);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ItemsAdapter extends BaseAdapter implements Filterable {
        public final List mAllItems;
        public final List mFilteredItems;

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        /* renamed from: com.android.server.autofill.ui.DialogFillUi$ItemsAdapter$1, reason: invalid class name */
        public final class AnonymousClass1 extends Filter {
            public AnonymousClass1() {
            }

            @Override // android.widget.Filter
            public final Filter.FilterResults performFiltering(final CharSequence charSequence) {
                List list = (List) ItemsAdapter.this.mAllItems.stream().filter(new Predicate() { // from class: com.android.server.autofill.ui.DialogFillUi$ItemsAdapter$1$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        CharSequence charSequence2 = charSequence;
                        DialogFillUi.ViewItem viewItem = (DialogFillUi.ViewItem) obj;
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
                    DialogFillUi dialogFillUi = DialogFillUi.this;
                    if (AccessibilityManager.getInstance(dialogFillUi.mContext).isEnabled()) {
                        if (dialogFillUi.mAnnounceFilterResult == null) {
                            dialogFillUi.mAnnounceFilterResult = dialogFillUi.new AnnounceFilterResult();
                        }
                        AnnounceFilterResult announceFilterResult = dialogFillUi.mAnnounceFilterResult;
                        DialogFillUi.this.mListView.removeCallbacks(announceFilterResult);
                        DialogFillUi.this.mListView.postDelayed(announceFilterResult, 1000L);
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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r14v2 */
    /* JADX WARN: Type inference failed for: r14v3 */
    /* JADX WARN: Type inference failed for: r14v4 */
    /* JADX WARN: Type inference failed for: r24v1, types: [java.util.regex.Pattern] */
    public DialogFillUi(Context context, FillResponse fillResponse, AutofillId autofillId, String str, Drawable drawable, String str2, ComponentName componentName, OverlayControl overlayControl, boolean z, AutoFillUI.AnonymousClass3 anonymousClass3) {
        int integer;
        boolean z2;
        String str3;
        Object obj;
        if (Helper.sVerbose) {
            Slog.v("DialogFillUi", "nightMode: " + z);
        }
        int i = z ? R.style.Theme.Leanback.Dialog : R.style.Theme.Material.Light.BaseDialog;
        this.mThemeId = i;
        this.mCallback = anonymousClass3;
        this.mOverlayControl = overlayControl;
        this.mServicePackageName = str2;
        this.mComponentName = componentName;
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, i);
        this.mContext = contextThemeWrapper;
        ViewGroup viewGroup = null;
        View inflate = LayoutInflater.from(contextThemeWrapper).inflate(R.layout.car_preference_category, (ViewGroup) null);
        if (fillResponse.getShowFillDialogIcon() && drawable != null) {
            ImageView imageView = (ImageView) inflate.findViewById(R.id.blocksDescendants);
            int minimumWidth = drawable.getMinimumWidth();
            int minimumHeight = drawable.getMinimumHeight();
            if (Helper.sDebug) {
                Slog.d("DialogFillUi", DualAppManagerService$$ExternalSyntheticOutline0.m(minimumWidth, minimumHeight, "Adding service icon (", "x", ")"));
            }
            imageView.setImageDrawable(drawable);
            imageView.setVisibility(0);
        }
        RemoteViews sanitizeRemoteView = Helper.sanitizeRemoteView(fillResponse.getDialogHeader());
        if (sanitizeRemoteView != null) {
            ViewGroup viewGroup2 = (ViewGroup) inflate.findViewById(R.id.autofill_sheet_divider);
            viewGroup2.addView(sanitizeRemoteView.applyWithTheme(contextThemeWrapper, (ViewGroup) inflate, new DialogFillUi$$ExternalSyntheticLambda5(this, 2), i));
            viewGroup2.setVisibility(0);
        }
        if (AutofillManagerService.getVisibleDatasetsMaxCount() > 0) {
            integer = AutofillManagerService.getVisibleDatasetsMaxCount();
            if (Helper.sVerbose) {
                ProxyManager$$ExternalSyntheticOutline0.m(integer, "overriding maximum visible datasets to ", "DialogFillUi");
            }
        } else {
            integer = contextThemeWrapper.getResources().getInteger(R.integer.autofill_max_visible_datasets);
        }
        this.mVisibleDatasetsMaxCount = integer;
        if (fillResponse.getAuthentication() != null) {
            this.mListView = null;
            this.mAdapter = null;
            try {
                initialAuthenticationLayout(inflate, fillResponse);
            } catch (RuntimeException e) {
                anonymousClass3.onCanceled();
                Slog.e("DialogFillUi", "Error inflating remote views", e);
                this.mDialog = null;
                return;
            }
        } else {
            int size = fillResponse.getDatasets().size();
            if (Helper.sVerbose) {
                Slog.v("DialogFillUi", "Number datasets: " + size + " max visible: " + integer);
            }
            DialogFillUi$$ExternalSyntheticLambda5 dialogFillUi$$ExternalSyntheticLambda5 = new DialogFillUi$$ExternalSyntheticLambda5(this, 1);
            ArrayList arrayList = new ArrayList(size);
            int i2 = 0;
            while (i2 < size) {
                Dataset dataset = (Dataset) fillResponse.getDatasets().get(i2);
                int indexOf = dataset.getFieldIds().indexOf(autofillId);
                if (indexOf >= 0) {
                    RemoteViews sanitizeRemoteView2 = Helper.sanitizeRemoteView(dataset.getFieldDialogPresentation(indexOf));
                    if (sanitizeRemoteView2 != null) {
                        try {
                            if (Helper.sVerbose) {
                                Slog.v("DialogFillUi", "setting remote view for " + autofillId);
                            }
                            View applyWithTheme = sanitizeRemoteView2.applyWithTheme(this.mContext, viewGroup, dialogFillUi$$ExternalSyntheticLambda5, this.mThemeId);
                            Dataset.DatasetFieldFilter filter = dataset.getFilter(indexOf);
                            if (filter == null) {
                                AutofillValue autofillValue = (AutofillValue) dataset.getFieldValues().get(indexOf);
                                str3 = (autofillValue == null || !autofillValue.isText()) ? viewGroup : autofillValue.getTextValue().toString().toLowerCase();
                                obj = viewGroup;
                                z2 = true;
                            } else {
                                Object obj2 = filter.pattern;
                                if (obj2 == null) {
                                    if (Helper.sVerbose) {
                                        Slog.v("DialogFillUi", "Explicitly disabling filter at id " + autofillId + " for dataset #" + indexOf);
                                    }
                                    z2 = false;
                                } else {
                                    z2 = true;
                                }
                                str3 = null;
                                obj = obj2;
                            }
                            arrayList.add(new ViewItem(dataset, obj, z2, str3, applyWithTheme));
                        } catch (RuntimeException e2) {
                            Slog.e("DialogFillUi", "Error inflating remote views", e2);
                        }
                    } else if (Helper.sDebug) {
                        Slog.w("DialogFillUi", "not displaying UI on field " + autofillId + " because service didn't provide a presentation for it on " + dataset);
                    }
                }
                i2++;
                viewGroup = null;
            }
            ItemsAdapter itemsAdapter = new ItemsAdapter(arrayList);
            this.mAdapter = itemsAdapter;
            ListView listView = (ListView) inflate.findViewById(R.id.autofill_sheet_scroll_view);
            this.mListView = listView;
            final AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() { // from class: com.android.server.autofill.ui.DialogFillUi$$ExternalSyntheticLambda2
                @Override // android.widget.AdapterView.OnItemClickListener
                public final void onItemClick(AdapterView adapterView, View view, int i3, long j) {
                    DialogFillUi dialogFillUi = DialogFillUi.this;
                    DialogFillUi.ViewItem viewItem = (DialogFillUi.ViewItem) ((ArrayList) dialogFillUi.mAdapter.mFilteredItems).get(i3);
                    AutoFillUI.AnonymousClass3 anonymousClass32 = dialogFillUi.mCallback;
                    Dataset dataset2 = viewItem.dataset;
                    anonymousClass32.log(4);
                    synchronized (anonymousClass32.val$sessionLock) {
                        PresentationStatsEventLogger presentationStatsEventLogger = anonymousClass32.val$presentationStatsEventLogger;
                        if (presentationStatsEventLogger != null) {
                            presentationStatsEventLogger.mEventInternal.ifPresent(new PresentationStatsEventLogger$$ExternalSyntheticLambda0(5));
                        }
                    }
                    AutoFillUI.this.hideFillDialogUiThread(anonymousClass32.val$callback);
                    if (AutoFillUI.this.mCallback != null) {
                        int indexOf2 = anonymousClass32.val$response.getDatasets().indexOf(dataset2);
                        ((Session) AutoFillUI.this.mCallback).fill(anonymousClass32.val$response.getRequestId(), indexOf2, dataset2, 3);
                    }
                }
            };
            listView.setAdapter((ListAdapter) itemsAdapter);
            listView.setVisibility(0);
            listView.setOnItemClickListener(onItemClickListener);
            if (itemsAdapter.getCount() == 1) {
                final int i3 = 0;
                View.OnClickListener onClickListener = new View.OnClickListener() { // from class: com.android.server.autofill.ui.DialogFillUi$$ExternalSyntheticLambda3
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        int i4 = i3;
                        Object obj3 = onItemClickListener;
                        switch (i4) {
                            case 0:
                                ((AdapterView.OnItemClickListener) obj3).onItemClick(null, null, 0, 0L);
                                return;
                            default:
                                AutoFillUI.AnonymousClass3 anonymousClass32 = ((DialogFillUi) obj3).mCallback;
                                anonymousClass32.log(5);
                                synchronized (anonymousClass32.val$sessionLock) {
                                    PresentationStatsEventLogger presentationStatsEventLogger = anonymousClass32.val$presentationStatsEventLogger;
                                    if (presentationStatsEventLogger != null) {
                                        presentationStatsEventLogger.mEventInternal.ifPresent(new PresentationStatsEventLogger$$ExternalSyntheticLambda0(4));
                                    }
                                }
                                AutoFillUI.this.hideFillDialogUiThread(anonymousClass32.val$callback);
                                ((Session) anonymousClass32.val$callback).requestShowSoftInput(anonymousClass32.val$focusedId);
                                ((Session) anonymousClass32.val$callback).requestFallbackFromFillDialog();
                                return;
                        }
                    }
                };
                TextView textView = (TextView) inflate.findViewById(R.id.back_button);
                textView.setText(R.string.config_credentialManagerReceiverComponent);
                textView.setOnClickListener(onClickListener);
                textView.setVisibility(0);
            }
            if (str == null) {
                this.mFilterText = null;
            } else {
                this.mFilterText = str.toLowerCase();
            }
            final int count = itemsAdapter.getCount();
            itemsAdapter.new AnonymousClass1().filter(this.mFilterText, new Filter.FilterListener() { // from class: com.android.server.autofill.ui.DialogFillUi$$ExternalSyntheticLambda4
                @Override // android.widget.Filter.FilterListener
                public final void onFilterComplete(int i4) {
                    DialogFillUi dialogFillUi = DialogFillUi.this;
                    int i5 = count;
                    if (dialogFillUi.mDestroyed) {
                        return;
                    }
                    if (i4 <= 0) {
                        if (Helper.sDebug) {
                            String str4 = dialogFillUi.mFilterText;
                            DeviceIdleController$$ExternalSyntheticOutline0.m(str4 != null ? str4.length() : 0, "No dataset matches filter with ", " chars", "DialogFillUi");
                        }
                        dialogFillUi.mCallback.onCanceled();
                        return;
                    }
                    DialogFillUi.ItemsAdapter itemsAdapter2 = dialogFillUi.mAdapter;
                    if (itemsAdapter2.getCount() > dialogFillUi.mVisibleDatasetsMaxCount) {
                        dialogFillUi.mListView.setVerticalScrollBarEnabled(true);
                        dialogFillUi.mListView.onVisibilityAggregated(true);
                    } else {
                        dialogFillUi.mListView.setVerticalScrollBarEnabled(false);
                    }
                    if (itemsAdapter2.getCount() != i5) {
                        dialogFillUi.mListView.requestLayout();
                    }
                }
            });
        }
        TextView textView2 = (TextView) inflate.findViewById(R.id.autofill_sheet_scroll_view_space);
        textView2.setText(R.string.config_datause_iface);
        final int i4 = 1;
        textView2.setOnClickListener(new View.OnClickListener() { // from class: com.android.server.autofill.ui.DialogFillUi$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                int i42 = i4;
                Object obj3 = this;
                switch (i42) {
                    case 0:
                        ((AdapterView.OnItemClickListener) obj3).onItemClick(null, null, 0, 0L);
                        return;
                    default:
                        AutoFillUI.AnonymousClass3 anonymousClass32 = ((DialogFillUi) obj3).mCallback;
                        anonymousClass32.log(5);
                        synchronized (anonymousClass32.val$sessionLock) {
                            PresentationStatsEventLogger presentationStatsEventLogger = anonymousClass32.val$presentationStatsEventLogger;
                            if (presentationStatsEventLogger != null) {
                                presentationStatsEventLogger.mEventInternal.ifPresent(new PresentationStatsEventLogger$$ExternalSyntheticLambda0(4));
                            }
                        }
                        AutoFillUI.this.hideFillDialogUiThread(anonymousClass32.val$callback);
                        ((Session) anonymousClass32.val$callback).requestShowSoftInput(anonymousClass32.val$focusedId);
                        ((Session) anonymousClass32.val$callback).requestFallbackFromFillDialog();
                        return;
                }
            }
        });
        Dialog dialog = new Dialog(this.mContext, this.mThemeId);
        this.mDialog = dialog;
        dialog.setContentView(inflate);
        Window window = dialog.getWindow();
        window.setType(2038);
        window.addFlags(131074);
        window.setDimAmount(0.6f);
        window.addPrivateFlags(16);
        window.setSoftInputMode(32);
        window.setGravity(81);
        window.setCloseOnTouchOutside(true);
        WindowManager.LayoutParams attributes = window.getAttributes();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        window.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        attributes.width = Math.min(displayMetrics.widthPixels, this.mContext.getResources().getDimensionPixelSize(R.dimen.button_bar_layout_start_padding));
        attributes.accessibilityTitle = this.mContext.getString(R.string.config_customAdbWifiNetworkConfirmationComponent);
        attributes.windowAnimations = R.style.DialogWindowTitle.Holo;
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.android.server.autofill.ui.DialogFillUi$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnCancelListener
            public final void onCancel(DialogInterface dialogInterface) {
                DialogFillUi.this.mCallback.onCanceled();
            }
        });
        dialog.setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.android.server.autofill.ui.DialogFillUi$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnShowListener
            public final void onShow(DialogInterface dialogInterface) {
                AutoFillUI.AnonymousClass3 anonymousClass32 = DialogFillUi.this.mCallback;
                ((Session) AutoFillUI.this.mCallback).onShown(3, anonymousClass32.val$response.getDatasets().size());
            }
        });
        Slog.i("DialogFillUi", "Showing fill dialog");
        dialog.show();
        this.mOverlayControl.setOverlayAllowed(false);
    }

    public final void initialAuthenticationLayout(View view, final FillResponse fillResponse) {
        RemoteViews sanitizeRemoteView = Helper.sanitizeRemoteView(fillResponse.getDialogPresentation());
        if (sanitizeRemoteView == null) {
            sanitizeRemoteView = Helper.sanitizeRemoteView(fillResponse.getPresentation());
        }
        if (sanitizeRemoteView == null) {
            throw new RuntimeException("No presentation for fill dialog authentication");
        }
        ViewGroup viewGroup = (ViewGroup) view.findViewById(R.id.autofill_service_icon);
        viewGroup.addView(sanitizeRemoteView.applyWithTheme(this.mContext, (ViewGroup) view, new DialogFillUi$$ExternalSyntheticLambda5(this, 0), this.mThemeId));
        viewGroup.setVisibility(0);
        viewGroup.setFocusable(true);
        final int i = 0;
        viewGroup.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.server.autofill.ui.DialogFillUi$$ExternalSyntheticLambda6
            public final /* synthetic */ DialogFillUi f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                switch (i) {
                    case 0:
                        DialogFillUi dialogFillUi = this.f$0;
                        dialogFillUi.mCallback.onResponsePicked(fillResponse);
                        break;
                    default:
                        DialogFillUi dialogFillUi2 = this.f$0;
                        dialogFillUi2.mCallback.onResponsePicked(fillResponse);
                        break;
                }
            }
        });
        final int i2 = 1;
        View.OnClickListener onClickListener = new View.OnClickListener(this) { // from class: com.android.server.autofill.ui.DialogFillUi$$ExternalSyntheticLambda6
            public final /* synthetic */ DialogFillUi f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                switch (i2) {
                    case 0:
                        DialogFillUi dialogFillUi = this.f$0;
                        dialogFillUi.mCallback.onResponsePicked(fillResponse);
                        break;
                    default:
                        DialogFillUi dialogFillUi2 = this.f$0;
                        dialogFillUi2.mCallback.onResponsePicked(fillResponse);
                        break;
                }
            }
        };
        TextView textView = (TextView) view.findViewById(R.id.back_button);
        textView.setText(R.string.config_credentialManagerReceiverComponent);
        textView.setOnClickListener(onClickListener);
        textView.setVisibility(0);
    }

    public final String toString() {
        return "NO TITLE";
    }
}
