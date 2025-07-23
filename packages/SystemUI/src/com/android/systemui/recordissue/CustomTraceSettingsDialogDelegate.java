package com.android.systemui.recordissue;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.traceur.PresetTraceConfigs;
import com.android.traceur.TraceConfig;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class CustomTraceSettingsDialogDelegate implements SystemUIDialog.Delegate {
    public final TraceConfig.Builder builder;
    public final CustomTraceState customTraceState;
    public final SystemUIDialog.Factory factory;
    public final Runnable onSave;
    public final Set tagTitles;

    public CustomTraceSettingsDialogDelegate(SystemUIDialog.Factory factory, CustomTraceState customTraceState, Set<String> set, Runnable runnable) {
        this.factory = factory;
        this.customTraceState = customTraceState;
        this.tagTitles = set;
        this.onSave = runnable;
        this.builder = new TraceConfig.Builder(customTraceState.getTraceConfig());
    }

    @Override // com.android.systemui.statusbar.phone.DialogDelegate
    public final void beforeCreate(Dialog dialog) {
        SystemUIDialog systemUIDialog = (SystemUIDialog) dialog;
        systemUIDialog.setTitle(R.string.custom_trace_settings_dialog_title);
        systemUIDialog.setView(LayoutInflater.from(systemUIDialog.getContext()).inflate(R.layout.custom_trace_settings_dialog, (ViewGroup) null));
        systemUIDialog.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() { // from class: com.android.systemui.recordissue.CustomTraceSettingsDialogDelegate$beforeCreate$1$1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                CustomTraceSettingsDialogDelegate.this.onSave.run();
                CustomTraceSettingsDialogDelegate customTraceSettingsDialogDelegate = CustomTraceSettingsDialogDelegate.this;
                CustomTraceState customTraceState = customTraceSettingsDialogDelegate.customTraceState;
                TraceConfig.Builder builder = customTraceSettingsDialogDelegate.builder;
                builder.getClass();
                TraceConfig traceConfig = new TraceConfig(builder.bufferSizeKb, builder.winscope, builder.apps, builder.longTrace, builder.attachToBugreport, builder.maxLongTraceSizeMb, builder.maxLongTraceDurationMinutes, builder.tags);
                customTraceState.getClass();
                customTraceState.prefs.edit().putStringSet("key_tags", traceConfig.tags).apply();
                PresetTraceConfigs.TraceOptions traceOptions = new PresetTraceConfigs.TraceOptions(traceConfig.bufferSizeKb, traceConfig.winscope, traceConfig.apps, traceConfig.longTrace, traceConfig.attachToBugreport, traceConfig.maxLongTraceSizeMb, traceConfig.maxLongTraceDurationMinutes);
                customTraceState.prefs.edit().putInt("key_bufferSizeKb", traceOptions.bufferSizeKb).putBoolean("key_winscope", traceOptions.winscope).putBoolean("key_apps", traceOptions.apps).putBoolean("key_longTrace", traceOptions.longTrace).putBoolean("key_attachToBugReport", traceOptions.attachToBugreport).putInt("key_maxLongTraceSizeMb", traceOptions.maxLongTraceSizeMb).putInt("key_maxLongTraceDurationInMinutes", traceOptions.maxLongTraceDurationMinutes).apply();
            }
        });
        systemUIDialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() { // from class: com.android.systemui.recordissue.CustomTraceSettingsDialogDelegate$beforeCreate$1$2
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
            }
        });
    }

    @Override // com.android.systemui.statusbar.phone.SystemUIDialog.Delegate
    public final SystemUIDialog createDialog() {
        SystemUIDialog.Factory factory = this.factory;
        return factory.create(this, factory.mContext);
    }

    @Override // com.android.systemui.statusbar.phone.DialogDelegate
    public final void onCreate(Dialog dialog, Bundle bundle) {
        String string;
        SystemUIDialog systemUIDialog = (SystemUIDialog) dialog;
        final TextView textView = (TextView) systemUIDialog.requireViewById(R.id.categories);
        String string2 = textView.getContext().getString(R.string.categories);
        TraceConfig.Builder builder = this.builder;
        Set set = builder.tags;
        if (set == null || set.equals(PresetTraceConfigs.getDefaultConfig().tags)) {
            string = textView.getContext().getString(R.string.notification_alert_title);
        } else {
            Set set2 = this.tagTitles;
            ArrayList arrayList = new ArrayList();
            for (Object obj : set2) {
                Set set3 = builder.tags;
                set3.getClass();
                if (set3.contains(StringsKt__StringsKt.substringBefore$default((String) obj, ": "))) {
                    arrayList.add(obj);
                }
            }
            string = CollectionsKt___CollectionsKt.joinToString$default(arrayList, null, null, null, null, 63);
        }
        textView.setText(string2 + "\n" + string);
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.recordissue.CustomTraceSettingsDialogDelegate$onCreate$1$1$2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                final CustomTraceSettingsDialogDelegate customTraceSettingsDialogDelegate = CustomTraceSettingsDialogDelegate.this;
                final TextView textView2 = textView;
                textView2.getClass();
                customTraceSettingsDialogDelegate.getClass();
                Context context = textView2.getContext();
                Function1 function1 = new Function1() { // from class: com.android.systemui.recordissue.CustomTraceSettingsDialogDelegate$$ExternalSyntheticLambda1
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj2) {
                        final TextView textView3 = textView2;
                        final AlertDialog.Builder builder2 = (AlertDialog.Builder) obj2;
                        final CustomTraceSettingsDialogDelegate customTraceSettingsDialogDelegate2 = CustomTraceSettingsDialogDelegate.this;
                        Set set4 = customTraceSettingsDialogDelegate2.builder.tags;
                        if (set4 == null) {
                            set4 = PresetTraceConfigs.getDefaultConfig().tags;
                        }
                        Set<String> set5 = customTraceSettingsDialogDelegate2.tagTitles;
                        int mapCapacity = MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(set5, 10));
                        if (mapCapacity < 16) {
                            mapCapacity = 16;
                        }
                        LinkedHashMap linkedHashMap = new LinkedHashMap(mapCapacity);
                        for (String str : set5) {
                            linkedHashMap.put(str, Boolean.valueOf(set4.contains(StringsKt__StringsKt.substringBefore$default(str, ": "))));
                        }
                        int i = 0;
                        final String[] strArr = (String[]) linkedHashMap.keySet().toArray(new String[0]);
                        boolean[] booleanArray = CollectionsKt___CollectionsKt.toBooleanArray(linkedHashMap.values());
                        Set entrySet = linkedHashMap.entrySet();
                        ArrayList arrayList2 = new ArrayList();
                        for (Object obj3 : entrySet) {
                            if (((Boolean) ((Map.Entry) obj3).getValue()).booleanValue()) {
                                arrayList2.add(obj3);
                            }
                        }
                        ArrayList arrayList3 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList2, 10));
                        int size = arrayList2.size();
                        while (i < size) {
                            Object obj4 = arrayList2.get(i);
                            i++;
                            arrayList3.add(StringsKt__StringsKt.substringAfter$default((String) ((Map.Entry) obj4).getKey(), ": "));
                        }
                        final Set mutableSet = CollectionsKt___CollectionsKt.toMutableSet(arrayList3);
                        set4.getClass();
                        final Set mutableSet2 = CollectionsKt___CollectionsKt.toMutableSet(set4);
                        builder2.setMultiChoiceItems(strArr, booleanArray, new DialogInterface.OnMultiChoiceClickListener() { // from class: com.android.systemui.recordissue.CustomTraceSettingsDialogDelegate$showCategorySelector$1$1
                            @Override // android.content.DialogInterface.OnMultiChoiceClickListener
                            public final void onClick(DialogInterface dialogInterface, int i2, boolean z) {
                                String substringBefore$default = StringsKt__StringsKt.substringBefore$default(strArr[i2], ": ");
                                String substringAfter$default = StringsKt__StringsKt.substringAfter$default(strArr[i2], ": ");
                                if (z) {
                                    mutableSet2.add(substringBefore$default);
                                    mutableSet.add(substringAfter$default);
                                } else {
                                    mutableSet2.remove(substringBefore$default);
                                    mutableSet.remove(substringAfter$default);
                                }
                            }
                        });
                        builder2.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() { // from class: com.android.systemui.recordissue.CustomTraceSettingsDialogDelegate$showCategorySelector$1$2
                            @Override // android.content.DialogInterface.OnClickListener
                            public final void onClick(DialogInterface dialogInterface, int i2) {
                                TextView textView4 = textView3;
                                textView4.setText(textView4.getContext().getResources().getString(R.string.categories) + "\n" + CollectionsKt___CollectionsKt.joinToString$default(mutableSet, null, null, null, null, 63));
                                customTraceSettingsDialogDelegate2.builder.tags = mutableSet2;
                            }
                        });
                        builder2.setNeutralButton(R.string.restore_default, new DialogInterface.OnClickListener() { // from class: com.android.systemui.recordissue.CustomTraceSettingsDialogDelegate$showCategorySelector$1$3
                            @Override // android.content.DialogInterface.OnClickListener
                            public final void onClick(DialogInterface dialogInterface, int i2) {
                                textView3.setText(builder2.getContext().getString(R.string.categories) + "\n" + builder2.getContext().getString(R.string.notification_alert_title));
                                customTraceSettingsDialogDelegate2.builder.tags = null;
                            }
                        });
                        builder2.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() { // from class: com.android.systemui.recordissue.CustomTraceSettingsDialogDelegate$showCategorySelector$1$4
                            @Override // android.content.DialogInterface.OnClickListener
                            public final void onClick(DialogInterface dialogInterface, int i2) {
                            }
                        });
                        return Unit.INSTANCE;
                    }
                };
                AlertDialog.Builder builder2 = new AlertDialog.Builder(context, R.style.Theme_SystemUI_Dialog_Alert);
                function1.mo779invoke(builder2);
                AlertDialog create = builder2.create();
                SystemUIDialog.applyFlags(create, true);
                create.show();
            }
        });
        String string3 = systemUIDialog.getContext().getString(R.string.attach_to_bug_report);
        Switch r1 = (Switch) systemUIDialog.requireViewById(R.id.attach_to_bugreport_switch);
        r1.setChecked(builder.attachToBugreport);
        r1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.android.systemui.recordissue.CustomTraceSettingsDialogDelegate$onCreate$1$2$1
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                CustomTraceSettingsDialogDelegate.this.builder.attachToBugreport = z;
            }
        });
        r1.setContentDescription(string3);
        setupSingleChoiceText((TextView) systemUIDialog.requireViewById(R.id.cpu_buffer_size), R.array.buffer_size_values, R.array.buffer_size_names, builder.bufferSizeKb, R.string.buffer_size, new Consumer() { // from class: com.android.systemui.recordissue.CustomTraceSettingsDialogDelegate$onCreate$1$3
            @Override // java.util.function.Consumer
            public final void accept(Object obj2) {
                CustomTraceSettingsDialogDelegate.this.builder.bufferSizeKb = ((Integer) obj2).intValue();
            }
        });
        final TextView textView2 = (TextView) systemUIDialog.requireViewById(R.id.long_trace_size);
        setupSingleChoiceText(textView2, R.array.long_trace_size_values, R.array.long_trace_size_names, builder.maxLongTraceSizeMb, R.string.max_long_trace_size, new Consumer() { // from class: com.android.systemui.recordissue.CustomTraceSettingsDialogDelegate$onCreate$1$longTraceSizeText$1
            @Override // java.util.function.Consumer
            public final void accept(Object obj2) {
                CustomTraceSettingsDialogDelegate.this.builder.maxLongTraceSizeMb = ((Integer) obj2).intValue();
            }
        });
        final TextView textView3 = (TextView) systemUIDialog.requireViewById(R.id.long_trace_duration);
        setupSingleChoiceText(textView3, R.array.long_trace_duration_values, R.array.long_trace_duration_names, builder.maxLongTraceDurationMinutes, R.string.max_long_trace_duration, new Consumer() { // from class: com.android.systemui.recordissue.CustomTraceSettingsDialogDelegate$onCreate$1$longTraceDurationText$1
            @Override // java.util.function.Consumer
            public final void accept(Object obj2) {
                CustomTraceSettingsDialogDelegate.this.builder.maxLongTraceDurationMinutes = ((Integer) obj2).intValue();
            }
        });
        String string4 = systemUIDialog.getContext().getString(R.string.long_traces);
        final Switch r3 = (Switch) systemUIDialog.requireViewById(R.id.long_traces_switch);
        r3.setChecked(builder.longTrace);
        final Lazy lazy = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.recordissue.CustomTraceSettingsDialogDelegate$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Context context = r3.getContext();
                CustomTraceSettingsDialogDelegate.this.getClass();
                TypedArray obtainStyledAttributes = context.obtainStyledAttributes(new int[]{android.R.attr.disabledAlpha});
                float f = obtainStyledAttributes.getFloat(0, 0.0f);
                obtainStyledAttributes.recycle();
                return Float.valueOf(f);
            }
        });
        float floatValue = r3.isChecked() ? 1.0f : ((Number) lazy.getValue()).floatValue();
        textView3.setAlpha(floatValue);
        textView2.setAlpha(floatValue);
        r3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.android.systemui.recordissue.CustomTraceSettingsDialogDelegate$onCreate$1$4$1
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                CustomTraceSettingsDialogDelegate.this.builder.longTrace = z;
                textView3.setEnabled(z);
                textView2.setEnabled(z);
                float floatValue2 = z ? 1.0f : ((Number) lazy.getValue()).floatValue();
                textView3.setAlpha(floatValue2);
                textView2.setAlpha(floatValue2);
            }
        });
        r3.setContentDescription(string4);
        String string5 = systemUIDialog.getContext().getString(R.string.winscope_tracing);
        Switch r32 = (Switch) systemUIDialog.requireViewById(R.id.winscope_switch);
        r32.setChecked(builder.winscope);
        r32.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.android.systemui.recordissue.CustomTraceSettingsDialogDelegate$onCreate$1$5$1
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                CustomTraceSettingsDialogDelegate.this.builder.winscope = z;
            }
        });
        r32.setContentDescription(string5);
        String string6 = systemUIDialog.getContext().getString(R.string.trace_debuggable_applications);
        Switch r4 = (Switch) systemUIDialog.requireViewById(R.id.trace_debuggable_apps_switch);
        r4.setChecked(builder.apps);
        r4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.android.systemui.recordissue.CustomTraceSettingsDialogDelegate$onCreate$1$6$1
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                CustomTraceSettingsDialogDelegate.this.builder.apps = z;
            }
        });
        r4.setContentDescription(string6);
        ((TextView) systemUIDialog.requireViewById(R.id.long_traces_switch_label)).setText(string4);
        ((TextView) systemUIDialog.requireViewById(R.id.debuggable_apps_switch_label)).setText(string6);
        ((TextView) systemUIDialog.requireViewById(R.id.winscope_switch_label)).setText(string5);
        ((TextView) systemUIDialog.requireViewById(R.id.attach_to_bugreport_switch_label)).setText(string3);
    }

    public final void setupSingleChoiceText(final TextView textView, int i, int i2, int i3, final int i4, final Consumer consumer) {
        String[] stringArray = textView.getResources().getStringArray(i);
        final ArrayList arrayList = new ArrayList(stringArray.length);
        for (String str : stringArray) {
            arrayList.add(Integer.valueOf(Integer.parseInt(str)));
        }
        final String[] stringArray2 = textView.getResources().getStringArray(i2);
        final int indexOf = arrayList.indexOf(Integer.valueOf(i3));
        textView.setText(textView.getResources().getString(i4) + "\n" + stringArray2[indexOf]);
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.recordissue.CustomTraceSettingsDialogDelegate$setupSingleChoiceText$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CustomTraceSettingsDialogDelegate customTraceSettingsDialogDelegate = CustomTraceSettingsDialogDelegate.this;
                Context context = textView.getContext();
                final int i5 = i4;
                final String[] strArr = stringArray2;
                int i6 = indexOf;
                final TextView textView2 = textView;
                final Consumer consumer2 = consumer;
                final List list = arrayList;
                customTraceSettingsDialogDelegate.getClass();
                AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.Theme_SystemUI_Dialog_Alert);
                builder.setTitle(i5);
                builder.setSingleChoiceItems(strArr, i6, new DialogInterface.OnClickListener() { // from class: com.android.systemui.recordissue.CustomTraceSettingsDialogDelegate$setupSingleChoiceText$1$1$1
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i7) {
                        TextView textView3 = textView2;
                        textView3.setText(textView3.getResources().getString(i5) + "\n" + strArr[i7]);
                        consumer2.accept(list.get(i7));
                        dialogInterface.dismiss();
                    }
                });
                Unit unit = Unit.INSTANCE;
                AlertDialog create = builder.create();
                SystemUIDialog.applyFlags(create, true);
                create.show();
            }
        });
    }
}
