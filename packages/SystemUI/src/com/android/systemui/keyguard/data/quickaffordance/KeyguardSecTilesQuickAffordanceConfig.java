package com.android.systemui.keyguard.data.quickaffordance;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.animation.Expandable;
import com.android.systemui.bixby2.controller.MWBixbyController$$ExternalSyntheticOutline0;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig;
import com.android.systemui.plugins.qs.LockQSTile;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.QSTileHost;
import com.android.systemui.qs.SecLockscreenTileHost;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.flow.Flow;

public final class KeyguardSecTilesQuickAffordanceConfig implements KeyguardQuickAffordanceConfig {
    public final KeyguardSecTilesQuickAffordanceConfig$callback$1 callback;
    public LockQSTile lockQSTile;
    public final SecLockscreenTileHost lockQSTileHost;
    public final Flow lockScreenState;
    public final int pickerIconResourceId;
    public String key = "tilesConfig";
    public final HashMap componentNameList = new HashMap();

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public KeyguardSecTilesQuickAffordanceConfig(Context context, QSTileHost qSTileHost) {
        Collection collection;
        this.lockQSTileHost = qSTileHost.mLockscreentTileHost;
        List split = new Regex(",").split(context.getResources().getString(R.string.lock_shortcut_custom_tile_component_name));
        if (!split.isEmpty()) {
            ListIterator listIterator = split.listIterator(split.size());
            while (listIterator.hasPrevious()) {
                if (((String) listIterator.previous()).length() != 0) {
                    collection = CollectionsKt___CollectionsKt.take(split, listIterator.nextIndex() + 1);
                    break;
                }
            }
        }
        collection = EmptyList.INSTANCE;
        for (String str : (String[]) collection.toArray(new String[0])) {
            int indexOf$default = StringsKt__StringsKt.indexOf$default((CharSequence) str, ":", 0, false, 6);
            String substring = str.substring(0, indexOf$default);
            String m = ContentInViewNode$Request$$ExternalSyntheticOutline0.m("custom(", str.substring(indexOf$default + 1, str.length()), ")");
            this.componentNameList.put(substring, m);
            Log.d("KeyguardSecTilesQuickAffordanceConfig", "make table : customTileName = " + substring + ", componentName = " + m);
        }
        this.pickerIconResourceId = R.drawable.fg_do_not_disturb_off;
        this.callback = KeyguardSecTilesQuickAffordanceConfig$callback$1.INSTANCE;
        this.lockScreenState = FlowConflatedKt.conflatedCallbackFlow(new KeyguardSecTilesQuickAffordanceConfig$lockScreenState$1(null));
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final void addListener() {
        LockQSTile lockQSTile = this.lockQSTile;
        KeyguardSecTilesQuickAffordanceConfig$callback$1 keyguardSecTilesQuickAffordanceConfig$callback$1 = this.callback;
        if (lockQSTile != null) {
            lockQSTile.addCallback(keyguardSecTilesQuickAffordanceConfig$callback$1);
        }
        LockQSTile lockQSTile2 = this.lockQSTile;
        if (lockQSTile2 != null) {
            lockQSTile2.setListening(keyguardSecTilesQuickAffordanceConfig$callback$1, true);
        }
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final Drawable getDrawable() {
        LockQSTile lockQSTile = this.lockQSTile;
        if (lockQSTile != null) {
            return lockQSTile.getTileIconDrawable();
        }
        return null;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final String getKey() {
        return this.key;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final Flow getLockScreenState() {
        return this.lockScreenState;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final Drawable getPanelIconTransitionDrawable(boolean z) {
        LockQSTile lockQSTile = this.lockQSTile;
        if (lockQSTile != null) {
            return lockQSTile.getNextTileIconDrawable();
        }
        return null;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final int getPickerIconResourceId() {
        return this.pickerIconResourceId;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final boolean isAvailable() {
        LockQSTile lockQSTile = this.lockQSTile;
        if (lockQSTile != null) {
            return lockQSTile.isAvailable();
        }
        return false;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final boolean isTaskEnabled() {
        QSTile.State state;
        LockQSTile lockQSTile = this.lockQSTile;
        return (lockQSTile == null || (state = lockQSTile.getState()) == null || state.state != 2) ? false : true;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final KeyguardQuickAffordanceConfig.OnTriggeredResult onTriggered(Expandable expandable) {
        LockQSTile lockQSTile = this.lockQSTile;
        if (lockQSTile != null) {
            lockQSTile.click();
        }
        return KeyguardQuickAffordanceConfig.OnTriggeredResult.Handled.INSTANCE;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final String pickerName() {
        LockQSTile lockQSTile = this.lockQSTile;
        return String.valueOf(lockQSTile != null ? lockQSTile.getTileLabel() : null);
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final void removeListener() {
        LockQSTile lockQSTile = this.lockQSTile;
        KeyguardSecTilesQuickAffordanceConfig$callback$1 keyguardSecTilesQuickAffordanceConfig$callback$1 = this.callback;
        if (lockQSTile != null) {
            lockQSTile.setListening(keyguardSecTilesQuickAffordanceConfig$callback$1, false);
        }
        LockQSTile lockQSTile2 = this.lockQSTile;
        if (lockQSTile2 != null) {
            lockQSTile2.removeCallback(keyguardSecTilesQuickAffordanceConfig$callback$1);
        }
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final void setSpecName(String str) {
        MWBixbyController$$ExternalSyntheticOutline0.m("old spec: ", this.key, ", new spec: ", str, "KeyguardSecTilesQuickAffordanceConfig");
        if (Intrinsics.areEqual(this.key, str)) {
            return;
        }
        String str2 = (String) this.componentNameList.get(this.key);
        if (str2 == null) {
            str2 = this.key;
        }
        Intrinsics.checkNotNull(str2);
        if (str == null) {
            str = "";
        }
        this.key = str;
        String str3 = (String) this.componentNameList.get(str);
        if (str3 == null) {
            str3 = this.key;
        }
        Intrinsics.checkNotNull(str3);
        StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("updated key: ", this.key, ", new key: ", str3, ", old key: ");
        m.append(str2);
        Log.d("KeyguardSecTilesQuickAffordanceConfig", m.toString());
        boolean isEmpty = TextUtils.isEmpty(str2);
        SecLockscreenTileHost secLockscreenTileHost = this.lockQSTileHost;
        if (!isEmpty) {
            secLockscreenTileHost.getClass();
            Log.d(secLockscreenTileHost.TAG, "releaseLockscreenTile  ".concat(str2));
            QSTileHost qSTileHost = secLockscreenTileHost.qsHost;
            String customTileSpecFromTileName = qSTileHost.getCustomTileSpecFromTileName(str2);
            if (customTileSpecFromTileName != null) {
                str2 = customTileSpecFromTileName;
            }
            qSTileHost.mQSTileInstanceManager.releaseTileUsing(secLockscreenTileHost.obj, str2);
        }
        LockQSTile lockQSTile = null;
        this.lockQSTile = null;
        if (TextUtils.isEmpty(str3)) {
            return;
        }
        secLockscreenTileHost.getClass();
        String concat = "requestLockscreenTile  ".concat(str3);
        String str4 = secLockscreenTileHost.TAG;
        Log.d(str4, concat);
        QSTileHost qSTileHost2 = secLockscreenTileHost.qsHost;
        String customTileSpecFromTileName2 = qSTileHost2.getCustomTileSpecFromTileName(str3);
        if (customTileSpecFromTileName2 != null) {
            str3 = customTileSpecFromTileName2;
        }
        QSTile requestTileUsing = qSTileHost2.mQSTileInstanceManager.requestTileUsing(secLockscreenTileHost.obj, str3);
        if (requestTileUsing instanceof LockQSTile) {
            lockQSTile = (LockQSTile) requestTileUsing;
        } else {
            Log.w(str4, "not LockQSTile object");
        }
        this.lockQSTile = lockQSTile;
    }
}
