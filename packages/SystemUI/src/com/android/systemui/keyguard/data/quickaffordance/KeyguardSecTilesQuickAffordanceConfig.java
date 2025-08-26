package com.android.systemui.keyguard.data.quickaffordance;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import com.android.settingslib.volume.MediaSessions$H$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.animation.Expandable;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig;
import com.android.systemui.plugins.qs.LockQSTile;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.SecLockscreenTileHost;
import com.android.systemui.qs.pipeline.data.repository.TileNameConverter;
import com.android.systemui.qs.pipeline.shared.TileSpec;
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

/* loaded from: classes2.dex */
public final class KeyguardSecTilesQuickAffordanceConfig implements KeyguardQuickAffordanceConfig {
    public final KeyguardSecTilesQuickAffordanceConfig$callback$1 callback;
    public boolean isLocalCustomTile;
    public LockQSTile lockQSTile;
    public final SecLockscreenTileHost lockQSTileHost;
    public final Flow lockScreenState;
    public final int pickerIconResourceId;
    public String key = "tilesConfig";
    public final HashMap componentNameList = new HashMap();

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public KeyguardSecTilesQuickAffordanceConfig(Context context, SecLockscreenTileHost secLockscreenTileHost) throws Resources.NotFoundException {
        Collection collectionTake;
        this.lockQSTileHost = secLockscreenTileHost;
        List listSplit = new Regex(",").split(context.getResources().getString(R.string.lock_shortcut_custom_tile_component_name));
        if (listSplit.isEmpty()) {
            collectionTake = EmptyList.INSTANCE;
        } else {
            ListIterator listIterator = listSplit.listIterator(listSplit.size());
            while (listIterator.hasPrevious()) {
                if (((String) listIterator.previous()).length() != 0) {
                    collectionTake = CollectionsKt___CollectionsKt.take(listSplit, listIterator.nextIndex() + 1);
                    break;
                }
            }
            collectionTake = EmptyList.INSTANCE;
        }
        for (String str : (String[]) collectionTake.toArray(new String[0])) {
            str.getClass();
            int iIndexOf$default = StringsKt__StringsKt.indexOf$default(str, ":", 0, false, 6);
            String strSubstring = str.substring(0, iIndexOf$default);
            String strSubstring2 = str.substring(iIndexOf$default + 1, str.length());
            if (strSubstring2 != null) {
                strSubstring2 = ContentInViewNode$Request$$ExternalSyntheticOutline0.m("custom(", strSubstring2, ")");
            }
            this.componentNameList.put(strSubstring, strSubstring2);
            Log.d("KeyguardSecTilesQuickAffordanceConfig", "make table : customTileName = " + strSubstring + ", componentName = " + strSubstring2);
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
    public final Drawable getPanelIconTransitionDrawable() {
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
    public final boolean isIconPaddingRequired() {
        return this.isLocalCustomTile;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final boolean isTaskEnabled() {
        QSTile.State state;
        LockQSTile lockQSTile = this.lockQSTile;
        return (lockQSTile == null || (state = lockQSTile.getState()) == null || state.state != 2) ? false : true;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final boolean isUnlockWaitRequired() {
        boolean z = Intrinsics.areEqual(this.key, "LiveTranscribe") || Intrinsics.areEqual(this.key, "QRScanner") || Intrinsics.areEqual(this.key, "CreateNote");
        Log.d("KeyguardSecTilesQuickAffordanceConfig", "isUnlockWaitRequired " + z + " key: " + this.key);
        return z;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final KeyguardQuickAffordanceConfig.OnTriggeredResult onTriggered(Expandable expandable) {
        LockQSTile lockQSTile = this.lockQSTile;
        if (lockQSTile != null) {
            lockQSTile.click();
        }
        return new KeyguardQuickAffordanceConfig.OnTriggeredResult.Handled(true);
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
        MediaSessions$H$$ExternalSyntheticOutline0.m("old spec: ", this.key, ", new spec: ", str, "KeyguardSecTilesQuickAffordanceConfig");
        if (this.key.equals(str)) {
            return;
        }
        String str2 = (String) this.componentNameList.get(this.key);
        if (str2 == null) {
            str2 = this.key;
        }
        if (str == null) {
            str = "";
        }
        this.key = str;
        this.isLocalCustomTile = !TextUtils.isEmpty((CharSequence) this.componentNameList.get(str));
        String str3 = (String) this.componentNameList.get(this.key);
        if (str3 == null) {
            str3 = this.key;
        }
        String str4 = this.key;
        boolean z = this.isLocalCustomTile;
        StringBuilder sbM = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("updated key: ", str4, ", new key: ", str3, ", old key: ");
        sbM.append(str2);
        sbM.append(", isLocalCustomTile: ");
        sbM.append(z);
        Log.d("KeyguardSecTilesQuickAffordanceConfig", sbM.toString());
        boolean zIsEmpty = TextUtils.isEmpty(str2);
        SecLockscreenTileHost secLockscreenTileHost = this.lockQSTileHost;
        if (!zIsEmpty) {
            secLockscreenTileHost.getClass();
            Log.d("LockscreenTileHost", "releaseLockscreenTile  ".concat(str2));
            TileSpec.Companion companion = TileSpec.Companion;
            TileNameConverter tileNameConverter = TileNameConverter.INSTANCE;
            Resources resources = secLockscreenTileHost.resources;
            tileNameConverter.getClass();
            String tileSpec = TileNameConverter.toTileSpec(resources, str2);
            companion.getClass();
            secLockscreenTileHost.tileInstanceManager.releaseTileUsing("Lock", TileSpec.Companion.create(tileSpec));
        }
        LockQSTile lockQSTile = null;
        this.lockQSTile = null;
        if (TextUtils.isEmpty(str3)) {
            return;
        }
        secLockscreenTileHost.getClass();
        Log.d("LockscreenTileHost", "requestLockscreenTile  ".concat(str3));
        TileSpec.Companion companion2 = TileSpec.Companion;
        TileNameConverter tileNameConverter2 = TileNameConverter.INSTANCE;
        Resources resources2 = secLockscreenTileHost.resources;
        tileNameConverter2.getClass();
        String tileSpec2 = TileNameConverter.toTileSpec(resources2, str3);
        companion2.getClass();
        QSTile qSTileRequestTileUsing = secLockscreenTileHost.tileInstanceManager.requestTileUsing("Lock", TileSpec.Companion.create(tileSpec2));
        if (qSTileRequestTileUsing instanceof LockQSTile) {
            lockQSTile = (LockQSTile) qSTileRequestTileUsing;
        } else {
            Log.w("LockscreenTileHost", "not LockQSTile object");
        }
        this.lockQSTile = lockQSTile;
    }
}
