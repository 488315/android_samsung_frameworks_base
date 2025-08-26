package androidx.compose.material3;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.ui.graphics.Color;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
final class DefaultDrawerItemsColor implements NavigationDrawerItemColors {
    public final long selectedBadgeColor;
    public final long selectedContainerColor;
    public final long selectedIconColor;
    public final long selectedTextColor;
    public final long unselectedBadgeColor;
    public final long unselectedContainerColor;
    public final long unselectedIconColor;
    public final long unselectedTextColor;

    public /* synthetic */ DefaultDrawerItemsColor(long j, long j2, long j3, long j4, long j5, long j6, long j7, long j8, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, j2, j3, j4, j5, j6, j7, j8);
    }

    @Override // androidx.compose.material3.NavigationDrawerItemColors
    public final MutableState containerColor(boolean z, Composer composer, int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(-433512770);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.material3.DefaultDrawerItemsColor.containerColor (NavigationDrawer.kt:1252)");
        }
        MutableState mutableStateRememberUpdatedState = SnapshotStateKt.rememberUpdatedState(Color.m456boximpl(z ? this.selectedContainerColor : this.unselectedContainerColor), composerImpl);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return mutableStateRememberUpdatedState;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DefaultDrawerItemsColor)) {
            return false;
        }
        DefaultDrawerItemsColor defaultDrawerItemsColor = (DefaultDrawerItemsColor) obj;
        long j = defaultDrawerItemsColor.selectedIconColor;
        Color.Companion companion = Color.Companion;
        if (!ULong.m3447equalsimpl0(this.selectedIconColor, j)) {
            return false;
        }
        if (!ULong.m3447equalsimpl0(this.unselectedIconColor, defaultDrawerItemsColor.unselectedIconColor)) {
            return false;
        }
        if (!ULong.m3447equalsimpl0(this.selectedTextColor, defaultDrawerItemsColor.selectedTextColor)) {
            return false;
        }
        if (!ULong.m3447equalsimpl0(this.unselectedTextColor, defaultDrawerItemsColor.unselectedTextColor)) {
            return false;
        }
        if (!ULong.m3447equalsimpl0(this.selectedContainerColor, defaultDrawerItemsColor.selectedContainerColor)) {
            return false;
        }
        if (!ULong.m3447equalsimpl0(this.unselectedContainerColor, defaultDrawerItemsColor.unselectedContainerColor)) {
            return false;
        }
        if (!ULong.m3447equalsimpl0(this.selectedBadgeColor, defaultDrawerItemsColor.selectedBadgeColor)) {
            return false;
        }
        return ULong.m3447equalsimpl0(this.unselectedBadgeColor, defaultDrawerItemsColor.unselectedBadgeColor);
    }

    public final int hashCode() {
        Color.Companion companion = Color.Companion;
        int i = ULong.$r8$clinit;
        return Long.hashCode(this.unselectedBadgeColor) + MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(Long.hashCode(this.selectedIconColor) * 31, 31, this.unselectedIconColor), 31, this.selectedTextColor), 31, this.unselectedTextColor), 31, this.selectedContainerColor), 31, this.unselectedContainerColor), 31, this.selectedBadgeColor);
    }

    @Override // androidx.compose.material3.NavigationDrawerItemColors
    public final MutableState iconColor(boolean z, Composer composer) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(1141354218);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.material3.DefaultDrawerItemsColor.iconColor (NavigationDrawer.kt:1242)");
        }
        MutableState mutableStateRememberUpdatedState = SnapshotStateKt.rememberUpdatedState(Color.m456boximpl(z ? this.selectedIconColor : this.unselectedIconColor), composerImpl);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return mutableStateRememberUpdatedState;
    }

    @Override // androidx.compose.material3.NavigationDrawerItemColors
    public final MutableState textColor(boolean z, Composer composer) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(1275109558);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.material3.DefaultDrawerItemsColor.textColor (NavigationDrawer.kt:1247)");
        }
        MutableState mutableStateRememberUpdatedState = SnapshotStateKt.rememberUpdatedState(Color.m456boximpl(z ? this.selectedTextColor : this.unselectedTextColor), composerImpl);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return mutableStateRememberUpdatedState;
    }

    private DefaultDrawerItemsColor(long j, long j2, long j3, long j4, long j5, long j6, long j7, long j8) {
        this.selectedIconColor = j;
        this.unselectedIconColor = j2;
        this.selectedTextColor = j3;
        this.unselectedTextColor = j4;
        this.selectedContainerColor = j5;
        this.unselectedContainerColor = j6;
        this.selectedBadgeColor = j7;
        this.unselectedBadgeColor = j8;
    }
}
