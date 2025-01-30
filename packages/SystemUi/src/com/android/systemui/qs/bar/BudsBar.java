package com.android.systemui.qs.bar;

import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.net.Uri;
import android.provider.Settings;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0;
import androidx.core.widget.NestedScrollView$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.AbstractC0866xb1ce8deb;
import com.android.systemui.FontSizeUtils;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.media.MediaBluetoothHelper;
import com.android.systemui.qs.SecQSPanelController;
import com.android.systemui.qs.SecQSPanelControllerBase;
import com.android.systemui.plugins.qs.DetailAdapter;
import com.android.systemui.util.ConfigurationState;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.volume.util.BluetoothA2dpUtil;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class BudsBar extends BarItemImpl {
    public static final byte[] BATTERY_TAG_KEYS;
    public final BroadcastDispatcher broadcastDispatcher;
    public final BudsBar$broadcastReceiver$1 broadcastReceiver;
    public TextView budsBatteries;
    public LinearLayout budsButton;
    public LinearLayout budsContainer;
    public boolean budsContentsUpdated;
    public boolean budsEnabled;
    public ImageView budsIcon;
    public View budsParent;
    public TextView budsText;
    public final Context context;
    public float fontScale;
    public final ConfigurationState lastConfigurationState;
    public LinearLayout marginView;
    public final MediaBluetoothHelper mediaBluetoothHelper;
    public int orientation;
    public final Lazy qsPanelControllerLazy;
    public boolean receiverRegistered;
    public final SettingsHelper settingsHelper;
    public final BudsBar$settingsListener$1 settingsListener;
    public Lazy soundCraftAdapter;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
        BATTERY_TAG_KEYS = new byte[]{8, 2, 9, 2};
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.qs.bar.BudsBar$settingsListener$1] */
    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.qs.bar.BudsBar$broadcastReceiver$1] */
    public BudsBar(Context context, Lazy lazy, SettingsHelper settingsHelper, BroadcastDispatcher broadcastDispatcher, MediaBluetoothHelper mediaBluetoothHelper) {
        super(context);
        this.context = context;
        this.qsPanelControllerLazy = lazy;
        this.settingsHelper = settingsHelper;
        this.broadcastDispatcher = broadcastDispatcher;
        this.mediaBluetoothHelper = mediaBluetoothHelper;
        this.settingsListener = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.qs.bar.BudsBar$settingsListener$1
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                if (uri != null && Intrinsics.areEqual(uri, Settings.System.getUriFor("buds_enable"))) {
                    BudsBar budsBar = BudsBar.this;
                    boolean budsEnable = budsBar.settingsHelper.getBudsEnable();
                    budsBar.budsEnabled = budsEnable;
                    Log.d(budsBar.TAG, AbstractC0866xb1ce8deb.m86m("onChanged(): buds enabled: ", budsEnable));
                    if (budsBar.mListening) {
                        budsBar.updateBroadcastDispatcher(budsBar.budsEnabled);
                    }
                    if (!budsBar.budsEnabled) {
                        SecQSPanelController secQSPanelController = (SecQSPanelController) budsBar.qsPanelControllerLazy.get();
                        Lazy lazy2 = budsBar.soundCraftAdapter;
                        if (lazy2 == null) {
                            lazy2 = null;
                        }
                        DetailAdapter detailAdapter = (DetailAdapter) lazy2.get();
                        SecQSPanelControllerBase.Record record = secQSPanelController.mDetailRecord;
                        if (record != null && record.mDetailAdapter == detailAdapter) {
                            secQSPanelController.showDetail(record, false);
                        }
                    }
                    budsBar.updateBarContents(null);
                    budsBar.updateBarVisibility();
                }
            }
        };
        this.broadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.qs.bar.BudsBar$broadcastReceiver$1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                if (intent != null) {
                    if (!Intrinsics.areEqual(intent.getAction(), "com.samsung.bluetooth.device.action.META_DATA_CHANGED")) {
                        intent = null;
                    }
                    if (intent != null) {
                        BudsBar budsBar = BudsBar.this;
                        Log.d(budsBar.TAG, "onReceive");
                        byte[] byteArrayExtra = intent.getByteArrayExtra("com.samsung.bluetooth.device.extra.META_DATA");
                        byte[] bArr = BudsBar.BATTERY_TAG_KEYS;
                        budsBar.updateBarContents(byteArrayExtra);
                    }
                }
            }
        };
        this.lastConfigurationState = new ConfigurationState(CollectionsKt__CollectionsKt.listOf(ConfigurationState.ConfigurationField.ORIENTATION, ConfigurationState.ConfigurationField.SCREEN_HEIGHT_DP, ConfigurationState.ConfigurationField.DISPLAY_DEVICE_TYPE, ConfigurationState.ConfigurationField.UI_MODE));
    }

    public static final SpannableStringBuilder toBatteriesString$toColoredStringBuilder(String str, BudsBar budsBar) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(str);
        spannableStringBuilder.setSpan(new ForegroundColorSpan(budsBar.context.getColor(R.color.qs_tile_sub_label)), 0, str.length(), 33);
        return spannableStringBuilder;
    }

    public static final void updateWeights$updateWeight(LinearLayout linearLayout, float f) {
        if (linearLayout.getContext().getResources().getConfiguration().orientation != 2) {
            f = 1.0f;
        }
        ((LinearLayout.LayoutParams) linearLayout.getLayoutParams()).weight = f;
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void destroy() {
        this.mCallback = null;
        this.settingsHelper.unregisterCallback(this.settingsListener);
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final int getBarLayout() {
        return R.layout.sec_buds_bar;
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void inflateViews(ViewGroup viewGroup) {
        Context context = this.context;
        View inflate = LayoutInflater.from(context).inflate(R.layout.sec_buds_bar, viewGroup, false);
        if (inflate != null) {
            View inflate2 = LayoutInflater.from(context).inflate(R.layout.sec_buds_button, (ViewGroup) inflate, false);
            if (inflate2 != null) {
                LinearLayout linearLayout = (LinearLayout) inflate2.findViewById(R.id.buds_button);
                if (linearLayout != null) {
                    linearLayout.setBackground(linearLayout.getContext().getDrawable(R.drawable.sec_large_button_ripple_background));
                } else {
                    linearLayout = null;
                }
                this.budsButton = linearLayout;
                this.marginView = (LinearLayout) inflate2.findViewById(R.id.margin_view);
                this.budsContainer = (LinearLayout) inflate2.findViewById(R.id.buds_container);
                this.budsIcon = (ImageView) inflate2.findViewById(R.id.buds_icon);
                TextView textView = (TextView) inflate2.findViewById(R.id.buds_text);
                if (textView != null) {
                    textView.setSelected(true);
                    textView.setTextAlignment(5);
                    textView.setLayoutDirection(3);
                } else {
                    textView = null;
                }
                this.budsText = textView;
                TextView textView2 = (TextView) inflate2.findViewById(R.id.buds_batteries);
                if (textView2 != null) {
                    textView2.setSelected(true);
                    textView2.setTextAlignment(5);
                    textView2.setLayoutDirection(3);
                } else {
                    textView2 = null;
                }
                this.budsBatteries = textView2;
                LinearLayout linearLayout2 = (LinearLayout) inflate.findViewById(R.id.slot_button_group);
                if (linearLayout2 != null) {
                    linearLayout2.addView(inflate2);
                }
            } else {
                inflate2 = null;
            }
            this.budsParent = inflate2;
        } else {
            inflate = null;
        }
        this.mBarRootView = inflate;
        Uri[] uriArr = {Settings.System.getUriFor("buds_enable")};
        SettingsHelper settingsHelper = this.settingsHelper;
        settingsHelper.registerCallback(this.settingsListener, uriArr);
        this.budsEnabled = settingsHelper.getBudsEnable();
        LinearLayout linearLayout3 = this.budsButton;
        if (linearLayout3 != null) {
            linearLayout3.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.bar.BudsBar$initialize$2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SecQSPanelController secQSPanelController = (SecQSPanelController) BudsBar.this.qsPanelControllerLazy.get();
                    Lazy lazy = BudsBar.this.soundCraftAdapter;
                    if (lazy == null) {
                        lazy = null;
                    }
                    DetailAdapter detailAdapter = (DetailAdapter) lazy.get();
                    secQSPanelController.getClass();
                    SecQSPanelControllerBase.Record record = new SecQSPanelControllerBase.Record(0);
                    record.mDetailAdapter = detailAdapter;
                    secQSPanelController.showDetail(record, true);
                    SystemUIAnalytics.sendEventLog(SystemUIAnalytics.sCurrentScreenID, "QPPE1031");
                }
            });
        }
        updateBarContents(null);
        LinearLayout linearLayout4 = this.budsButton;
        if (linearLayout4 != null) {
            updateWeights$updateWeight(linearLayout4, 0.5f);
        }
        LinearLayout linearLayout5 = this.marginView;
        if (linearLayout5 != null) {
            updateWeights$updateWeight(linearLayout5, 1.5f);
        }
        updateBarVisibility();
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void onConfigChanged(Configuration configuration) {
        if (this.mBarRootView == null) {
            return;
        }
        Context context = this.context;
        int i = context.getResources().getConfiguration().orientation;
        float f = context.getResources().getConfiguration().fontScale;
        ConfigurationState configurationState = this.lastConfigurationState;
        if (!configurationState.needToUpdate(configuration) && this.orientation == i) {
            if (this.fontScale == f) {
                return;
            }
        }
        this.orientation = i;
        this.fontScale = f;
        updateHeightMargins();
        LinearLayout linearLayout = this.budsButton;
        if (linearLayout != null) {
            updateWeights$updateWeight(linearLayout, 0.5f);
        }
        LinearLayout linearLayout2 = this.marginView;
        if (linearLayout2 != null) {
            updateWeights$updateWeight(linearLayout2, 1.5f);
        }
        TextView textView = this.budsText;
        if (textView != null) {
            FontSizeUtils.updateFontSize(textView, R.dimen.sec_style_qs_tile_text_size, 0.8f, 1.3f);
        }
        TextView textView2 = this.budsBatteries;
        if (textView2 != null) {
            FontSizeUtils.updateFontSize(textView2, R.dimen.sec_style_qs_tile_second_text_size, 0.8f, 1.3f);
        }
        ColorStateList valueOf = ColorStateList.valueOf(context.getColor(R.color.qs_tile_no_round_icon_color));
        ImageView imageView = this.budsIcon;
        if (imageView != null) {
            imageView.setImageTintList(valueOf);
        }
        configurationState.update(configuration);
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void setListening(boolean z) {
        this.mListening = z;
        if (this.budsEnabled) {
            if ((!z || this.receiverRegistered) && (z || !this.receiverRegistered)) {
                return;
            }
            updateBroadcastDispatcher(z);
            if (z) {
                updateBarContents(null);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [kotlin.collections.EmptyList] */
    /* JADX WARN: Type inference failed for: r0v10, types: [java.util.ArrayList] */
    /* JADX WARN: Type inference failed for: r0v2, types: [java.lang.Iterable] */
    /* JADX WARN: Type inference failed for: r0v9, types: [java.util.List] */
    public final Integer toBattery(byte[] bArr, byte b) {
        ?? r0;
        Object obj;
        int length = bArr.length;
        if (length == 0) {
            r0 = EmptyList.INSTANCE;
        } else if (length != 1) {
            r0 = new ArrayList(bArr.length);
            for (byte b2 : bArr) {
                r0.add(Byte.valueOf(b2));
            }
        } else {
            r0 = Collections.singletonList(Byte.valueOf(bArr[0]));
        }
        Iterator it = CollectionsKt___CollectionsKt.windowed$default(r0).iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            }
            obj = it.next();
            List list = (List) obj;
            if (((Number) list.get(0)).byteValue() == b && ((Number) list.get(1)).byteValue() == 2 && ((Number) list.get(2)).byteValue() == 1) {
                break;
            }
        }
        List list2 = (List) obj;
        String str = this.TAG;
        if (list2 != null) {
            Byte b3 = (Byte) (list2.isEmpty() ? null : list2.get(list2.size() - 1));
            if (b3 != null) {
                byte byteValue = b3.byteValue();
                Log.d(str, "toBattery: tagKey:" + ((int) b) + ": " + ((int) byteValue));
                return Integer.valueOf(byteValue);
            }
        }
        NestedScrollView$$ExternalSyntheticOutline0.m34m("toBattery: cannot find ", b, str);
        return null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0021, code lost:
    
        if (r0 == null) goto L14;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateBarContents(byte[] bArr) {
        List list;
        TextView textView;
        if (this.budsEnabled) {
            BluetoothA2dp bluetoothA2dp = this.mediaBluetoothHelper.a2dp;
            Pair pair = null;
            if (bluetoothA2dp != null) {
                BluetoothA2dpUtil.INSTANCE.getClass();
                list = BluetoothA2dpUtil.getOrderConnectedDevices(bluetoothA2dp);
                if (list != null) {
                    if (!(!list.isEmpty())) {
                        list = null;
                    }
                }
            }
            list = EmptyList.INSTANCE;
            BluetoothDevice bluetoothDevice = (BluetoothDevice) CollectionsKt___CollectionsKt.firstOrNull(list);
            String str = this.TAG;
            if (bluetoothDevice == null) {
                Log.e(str, "updateBarContents: connected device is empty");
                this.budsContentsUpdated = false;
                return;
            }
            TextView textView2 = this.budsText;
            if (textView2 != null) {
                String name = bluetoothDevice.getName();
                Log.d(str, "updateBarContents: name: " + name);
                textView2.setText(name);
            }
            if (bArr == null) {
                byte[] semGetMetadata = bluetoothDevice.semGetMetadata(BATTERY_TAG_KEYS);
                if (semGetMetadata != null) {
                    Integer battery = toBattery(semGetMetadata, (byte) 8);
                    int intValue = battery != null ? battery.intValue() : 0;
                    Integer battery2 = toBattery(semGetMetadata, (byte) 9);
                    pair = new Pair(Integer.valueOf(intValue), Integer.valueOf(battery2 != null ? battery2.intValue() : 0));
                } else {
                    Log.e(str, "getBatteries: cannot get metadata");
                }
            } else {
                Integer battery3 = toBattery(bArr, (byte) 8);
                if (battery3 != null) {
                    int intValue2 = battery3.intValue();
                    Integer battery4 = toBattery(bArr, (byte) 9);
                    if (battery4 != null) {
                        pair = new Pair(Integer.valueOf(intValue2), Integer.valueOf(battery4.intValue()));
                    }
                }
            }
            if (pair != null && (textView = this.budsBatteries) != null) {
                boolean z = MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0.m11m(this.context) == 1;
                String str2 = z ? "R" : "L";
                String str3 = z ? "L" : "R";
                int intValue3 = ((Number) (z ? pair.getSecond() : pair.getFirst())).intValue();
                int intValue4 = ((Number) (z ? pair.getFirst() : pair.getSecond())).intValue();
                SpannableStringBuilder append = new SpannableStringBuilder(str2).append((CharSequence) toBatteriesString$toColoredStringBuilder(" " + intValue3 + " · ", this)).append((CharSequence) str3).append((CharSequence) toBatteriesString$toColoredStringBuilder(" " + intValue4 + "%", this));
                StringBuilder sb = new StringBuilder("updateBarContents: batteries: ");
                sb.append((Object) append);
                Log.d(str, sb.toString());
                textView.setText(append);
            }
            this.budsContentsUpdated = true;
        }
    }

    public final void updateBarVisibility() {
        Log.d(this.TAG, "updateBarVisibility: budsEnabled: " + this.budsEnabled + ", budsContentsUpdated: " + this.budsContentsUpdated);
        View view = this.budsParent;
        boolean z = false;
        if (view != null) {
            view.setVisibility((this.budsEnabled && this.budsContentsUpdated) ? 0 : 8);
        }
        if (this.budsEnabled && this.budsContentsUpdated) {
            z = true;
        }
        showBar(z);
    }

    public final void updateBroadcastDispatcher(boolean z) {
        BroadcastDispatcher broadcastDispatcher = this.broadcastDispatcher;
        String str = this.TAG;
        if (z) {
            BroadcastDispatcher.registerReceiver$default(broadcastDispatcher, this.broadcastReceiver, new IntentFilter("com.samsung.bluetooth.device.action.META_DATA_CHANGED"), null, null, 0, null, 60);
            Log.d(str, "updateBroadcastDispatcher: register");
            this.receiverRegistered = true;
        } else {
            broadcastDispatcher.unregisterReceiver(this.broadcastReceiver);
            Log.d(str, "updateBroadcastDispatcher: unregister");
            this.receiverRegistered = false;
        }
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void updateHeightMargins() {
        Unit unit;
        View view = this.mBarRootView;
        Context context = this.context;
        if (view != null) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
            layoutParams.topMargin = context.getResources().getDimensionPixelSize(R.dimen.bar_top_margin);
            view.setLayoutParams(layoutParams);
            unit = Unit.INSTANCE;
        } else {
            unit = null;
        }
        if (unit == null) {
            return;
        }
        LinearLayout linearLayout = this.budsContainer;
        if (linearLayout != null) {
            linearLayout.setPaddingRelative(context.getResources().getDimensionPixelSize(R.dimen.buds_text_container_start_padding), linearLayout.getPaddingTop(), context.getResources().getDimensionPixelSize(R.dimen.buds_text_container_end_padding), linearLayout.getPaddingBottom());
        }
        TextView textView = this.budsBatteries;
        if (textView != null) {
            textView.setPaddingRelative(context.getResources().getDimensionPixelSize(R.dimen.buds_sub_text_start_padding), textView.getPaddingTop(), textView.getPaddingEnd(), textView.getPaddingBottom());
        }
    }
}
