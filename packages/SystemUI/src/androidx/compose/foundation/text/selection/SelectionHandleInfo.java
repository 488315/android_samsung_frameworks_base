package androidx.compose.foundation.text.selection;

import androidx.compose.animation.BoundsAnimationElement$$ExternalSyntheticOutline0;
import androidx.compose.foundation.text.Handle;
import androidx.compose.ui.geometry.Offset;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SelectionHandleInfo {
    public final SelectionHandleAnchor anchor;
    public final Handle handle;
    public final long position;
    public final boolean visible;

    public /* synthetic */ SelectionHandleInfo(Handle handle, long j, SelectionHandleAnchor selectionHandleAnchor, boolean z, DefaultConstructorMarker defaultConstructorMarker) {
        this(handle, j, selectionHandleAnchor, z);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SelectionHandleInfo)) {
            return false;
        }
        SelectionHandleInfo selectionHandleInfo = (SelectionHandleInfo) obj;
        return this.handle == selectionHandleInfo.handle && Offset.m396equalsimpl0(this.position, selectionHandleInfo.position) && this.anchor == selectionHandleInfo.anchor && this.visible == selectionHandleInfo.visible;
    }

    public final int hashCode() {
        int hashCode = this.handle.hashCode() * 31;
        Offset.Companion companion = Offset.Companion;
        return Boolean.hashCode(this.visible) + ((this.anchor.hashCode() + MoveResult$$ExternalSyntheticOutline0.m(hashCode, 31, this.position)) * 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("SelectionHandleInfo(handle=");
        sb.append(this.handle);
        sb.append(", position=");
        sb.append((Object) Offset.m403toStringimpl(this.position));
        sb.append(", anchor=");
        sb.append(this.anchor);
        sb.append(", visible=");
        return BoundsAnimationElement$$ExternalSyntheticOutline0.m(sb, this.visible, ')');
    }

    private SelectionHandleInfo(Handle handle, long j, SelectionHandleAnchor selectionHandleAnchor, boolean z) {
        this.handle = handle;
        this.position = j;
        this.anchor = selectionHandleAnchor;
        this.visible = z;
    }
}
