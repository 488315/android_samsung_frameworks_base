package com.android.systemui.statusbar.notification.row;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.INotificationManager;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.systemui.statusbar.notification.row.ChannelEditorDialog;
import com.android.systemui.statusbar.notification.row.NotificationInfo;
import com.android.systemui.util.Assert;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.collections.EmptyList;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.FilteringSequence;
import kotlin.sequences.SequencesKt___SequencesKt;
import kotlin.sequences.SequencesKt___SequencesKt$sortedWith$1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ChannelEditorDialogController {
    public Drawable appIcon;
    public String appName;
    public Boolean appNotificationsCurrentlyEnabled;
    public Integer appUid;
    public final Context context;
    public ChannelEditorDialog dialog;
    public final ChannelEditorDialog.Builder dialogBuilder;
    public final INotificationManager noMan;
    public OnChannelEditorDialogFinishedListener onFinishListener;
    public NotificationInfo.OnSettingsClickListener onSettingsClickListener;
    public String packageName;
    public boolean prepared;
    public final List paddedChannels = new ArrayList();
    public final List providedChannels = new ArrayList();
    public final Map edits = new LinkedHashMap();
    public boolean appNotificationsEnabled = true;
    public final HashMap groupNameLookup = new HashMap();
    public final List channelGroupList = new ArrayList();
    public final int wmFlags = -2130444288;

    public ChannelEditorDialogController(Context context, INotificationManager iNotificationManager, ChannelEditorDialog.Builder builder) {
        this.noMan = iNotificationManager;
        this.dialogBuilder = builder;
        this.context = context.getApplicationContext();
    }

    public final void apply() {
        INotificationManager iNotificationManager;
        Iterator it = ((LinkedHashMap) this.edits).entrySet().iterator();
        while (true) {
            boolean hasNext = it.hasNext();
            iNotificationManager = this.noMan;
            if (!hasNext) {
                break;
            }
            Map.Entry entry = (Map.Entry) it.next();
            NotificationChannel notificationChannel = (NotificationChannel) entry.getKey();
            int intValue = ((Number) entry.getValue()).intValue();
            if (notificationChannel.getImportance() != intValue) {
                try {
                    notificationChannel.setImportance(intValue);
                    String str = this.packageName;
                    Intrinsics.checkNotNull(str);
                    Integer num = this.appUid;
                    Intrinsics.checkNotNull(num);
                    iNotificationManager.updateNotificationChannelForPackage(str, num.intValue(), notificationChannel);
                } catch (Exception e) {
                    Log.e("ChannelDialogController", "Unable to update notification importance", e);
                }
            }
        }
        if (Intrinsics.areEqual(Boolean.valueOf(this.appNotificationsEnabled), this.appNotificationsCurrentlyEnabled)) {
            return;
        }
        boolean z = this.appNotificationsEnabled;
        try {
            String str2 = this.packageName;
            Intrinsics.checkNotNull(str2);
            Integer num2 = this.appUid;
            Intrinsics.checkNotNull(num2);
            iNotificationManager.setNotificationsEnabledForPackage(str2, num2.intValue(), z);
        } catch (Exception e2) {
            Log.e("ChannelDialogController", "Error calling NoMan", e2);
        }
    }

    public final void done() {
        this.appIcon = null;
        this.appUid = null;
        this.packageName = null;
        this.appName = null;
        this.appNotificationsCurrentlyEnabled = null;
        ((LinkedHashMap) this.edits).clear();
        ((ArrayList) this.paddedChannels).clear();
        ((ArrayList) this.providedChannels).clear();
        this.groupNameLookup.clear();
        ChannelEditorDialog channelEditorDialog = this.dialog;
        (channelEditorDialog != null ? channelEditorDialog : null).dismiss();
    }

    public final void launchSettings(View view) {
        ArrayList arrayList = (ArrayList) this.providedChannels;
        NotificationChannel notificationChannel = arrayList.size() == 1 ? (NotificationChannel) arrayList.get(0) : null;
        NotificationInfo.OnSettingsClickListener onSettingsClickListener = this.onSettingsClickListener;
        if (onSettingsClickListener != null) {
            Integer num = this.appUid;
            Intrinsics.checkNotNull(num);
            ((NotificationGutsManager$$ExternalSyntheticLambda2) onSettingsClickListener).onClick(num.intValue(), notificationChannel);
        }
    }

    public final void prepareDialogForApp(String str, String str2, int i, Set set, Drawable drawable, NotificationInfo.OnSettingsClickListener onSettingsClickListener) {
        boolean z;
        List list;
        INotificationManager iNotificationManager = this.noMan;
        this.appName = str;
        this.packageName = str2;
        this.appUid = Integer.valueOf(i);
        this.appIcon = drawable;
        try {
            String str3 = this.packageName;
            Intrinsics.checkNotNull(str3);
            Integer num = this.appUid;
            Intrinsics.checkNotNull(num);
            z = iNotificationManager.areNotificationsEnabledForPackage(str3, num.intValue());
        } catch (Exception e) {
            Log.e("ChannelDialogController", "Error calling NoMan", e);
            z = false;
        }
        this.appNotificationsEnabled = z;
        this.onSettingsClickListener = onSettingsClickListener;
        this.appNotificationsCurrentlyEnabled = Boolean.valueOf(z);
        List list2 = this.channelGroupList;
        ArrayList arrayList = (ArrayList) list2;
        arrayList.clear();
        try {
            String str4 = this.packageName;
            Intrinsics.checkNotNull(str4);
            Integer num2 = this.appUid;
            Intrinsics.checkNotNull(num2);
            list = iNotificationManager.getNotificationChannelGroupsForPackage(str4, num2.intValue(), false).getList();
            if (!(list instanceof List)) {
                list = null;
            }
            if (list == null) {
                list = EmptyList.INSTANCE;
            }
        } catch (Exception e2) {
            Log.e("ChannelDialogController", "Error fetching channel groups", e2);
            list = EmptyList.INSTANCE;
        }
        arrayList.addAll(list);
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            NotificationChannelGroup notificationChannelGroup = (NotificationChannelGroup) it.next();
            if (notificationChannelGroup.getId() != null) {
                this.groupNameLookup.put(notificationChannelGroup.getId(), notificationChannelGroup.getName());
            }
        }
        ArrayList arrayList2 = (ArrayList) this.providedChannels;
        arrayList2.clear();
        arrayList2.addAll(set);
        List list3 = this.paddedChannels;
        ((ArrayList) list3).clear();
        CollectionsKt__MutableCollectionsKt.addAll(list3, SequencesKt___SequencesKt.take(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(set), 4));
        ArrayList arrayList3 = (ArrayList) list3;
        CollectionsKt__MutableCollectionsKt.addAll(list3, SequencesKt___SequencesKt.take(SequencesKt___SequencesKt.distinct(new FilteringSequence(new SequencesKt___SequencesKt$sortedWith$1(SequencesKt___SequencesKt.flatMap(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(list2), new Function1() { // from class: com.android.systemui.statusbar.notification.row.ChannelEditorDialogController$getDisplayableChannels$channels$1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return new FilteringSequence(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(((NotificationChannelGroup) obj).getChannels()), false, new Function1() { // from class: com.android.systemui.statusbar.notification.row.ChannelEditorDialogController$getDisplayableChannels$channels$1.1
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj2) {
                        NotificationChannel notificationChannel = (NotificationChannel) obj2;
                        return Boolean.valueOf(notificationChannel.getImportance() == 0 || notificationChannel.isImportanceLockedByCriticalDeviceFunction());
                    }
                });
            }
        }), new Comparator() { // from class: com.android.systemui.statusbar.notification.row.ChannelEditorDialogController$getDisplayableChannels$$inlined$compareBy$1
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                String id;
                String id2;
                NotificationChannel notificationChannel = (NotificationChannel) obj;
                CharSequence name = notificationChannel.getName();
                if (name == null || (id = name.toString()) == null) {
                    id = notificationChannel.getId();
                }
                NotificationChannel notificationChannel2 = (NotificationChannel) obj2;
                CharSequence name2 = notificationChannel2.getName();
                if (name2 == null || (id2 = name2.toString()) == null) {
                    id2 = notificationChannel2.getId();
                }
                return ComparisonsKt__ComparisonsKt.compareValues(id, id2);
            }
        }), false, new Function1() { // from class: com.android.systemui.statusbar.notification.row.ChannelEditorDialogController$padToFourChannels$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Boolean.valueOf(((ArrayList) ChannelEditorDialogController.this.paddedChannels).contains((NotificationChannel) obj));
            }
        })), 4 - arrayList3.size()));
        if (arrayList3.size() == 1 && Intrinsics.areEqual("miscellaneous", ((NotificationChannel) arrayList3.get(0)).getId())) {
            arrayList3.clear();
        }
        ChannelEditorDialog.Builder builder = this.dialogBuilder;
        builder.context = this.context;
        Context context = builder.context;
        if (context == null) {
            context = null;
        }
        ChannelEditorDialog channelEditorDialog = new ChannelEditorDialog(context, 2132018527);
        this.dialog = channelEditorDialog;
        Window window = channelEditorDialog.getWindow();
        if (window != null) {
            window.requestFeature(1);
        }
        ChannelEditorDialog channelEditorDialog2 = this.dialog;
        if (channelEditorDialog2 == null) {
            channelEditorDialog2 = null;
        }
        channelEditorDialog2.setTitle(" ");
        ChannelEditorDialog channelEditorDialog3 = this.dialog;
        ChannelEditorDialog channelEditorDialog4 = channelEditorDialog3 != null ? channelEditorDialog3 : null;
        channelEditorDialog4.setContentView(R.layout.notif_half_shelf);
        channelEditorDialog4.setCanceledOnTouchOutside(true);
        channelEditorDialog4.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.statusbar.notification.row.ChannelEditorDialogController$initDialog$1$1
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                OnChannelEditorDialogFinishedListener onChannelEditorDialogFinishedListener = ChannelEditorDialogController.this.onFinishListener;
                if (onChannelEditorDialogFinishedListener != null) {
                    onChannelEditorDialogFinishedListener.onChannelEditorDialogFinished();
                }
            }
        });
        final ChannelEditorListView channelEditorListView = (ChannelEditorListView) channelEditorDialog4.findViewById(R.id.half_shelf_container);
        if (channelEditorListView != null) {
            channelEditorListView.controller = this;
            channelEditorListView.appIcon = this.appIcon;
            channelEditorListView.appName = this.appName;
            channelEditorListView.channels = list3;
            channelEditorListView.updateRows();
        }
        channelEditorDialog4.setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.android.systemui.statusbar.notification.row.ChannelEditorDialogController$initDialog$1$3
            @Override // android.content.DialogInterface.OnShowListener
            public final void onShow(DialogInterface dialogInterface) {
                Iterator it2 = ((ArrayList) ChannelEditorDialogController.this.providedChannels).iterator();
                while (it2.hasNext()) {
                    NotificationChannel notificationChannel = (NotificationChannel) it2.next();
                    ChannelEditorListView channelEditorListView2 = channelEditorListView;
                    if (channelEditorListView2 != null) {
                        Assert.isMainThread();
                        Iterator it3 = ((ArrayList) channelEditorListView2.channelRows).iterator();
                        while (it3.hasNext()) {
                            final ChannelRow channelRow = (ChannelRow) it3.next();
                            if (Intrinsics.areEqual(channelRow.channel, notificationChannel)) {
                                ValueAnimator ofObject = ValueAnimator.ofObject(new ArgbEvaluator(), 0, Integer.valueOf(channelRow.highlightColor));
                                ofObject.setDuration(200L);
                                ofObject.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.notification.row.ChannelRow$playHighlight$1
                                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                                        ChannelRow.this.setBackgroundColor(((Integer) valueAnimator.getAnimatedValue()).intValue());
                                    }
                                });
                                ofObject.setRepeatMode(2);
                                ofObject.setRepeatCount(5);
                                ofObject.start();
                            }
                        }
                    }
                }
            }
        });
        TextView textView = (TextView) channelEditorDialog4.findViewById(R.id.done_button);
        if (textView != null) {
            textView.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.row.ChannelEditorDialogController$initDialog$1$4
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ChannelEditorDialogController.this.apply();
                    ChannelEditorDialogController.this.done();
                }
            });
        }
        TextView textView2 = (TextView) channelEditorDialog4.findViewById(R.id.see_more_button);
        if (textView2 != null) {
            textView2.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.row.ChannelEditorDialogController$initDialog$1$5
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ChannelEditorDialogController.this.launchSettings(view);
                    ChannelEditorDialogController.this.done();
                }
            });
        }
        Window window2 = channelEditorDialog4.getWindow();
        if (window2 != null) {
            window2.setBackgroundDrawable(new ColorDrawable(0));
            window2.addFlags(this.wmFlags);
            window2.setType(2017);
            window2.setWindowAnimations(android.R.style.Animation.InputMethod);
            WindowManager.LayoutParams attributes = window2.getAttributes();
            attributes.format = -3;
            attributes.setTitle("ChannelEditorDialogController");
            attributes.gravity = 81;
            attributes.setFitInsetsTypes(window2.getAttributes().getFitInsetsTypes() & (~WindowInsets.Type.statusBars()));
            attributes.width = -1;
            attributes.height = -2;
            window2.setAttributes(attributes);
        }
        this.prepared = true;
    }

    /* renamed from: getGroupNameLookup$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations */
    public static /* synthetic */ void m204xe31ae654() {
    }

    /* renamed from: getPaddedChannels$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations */
    public static /* synthetic */ void m205x59c1b43c() {
    }
}
