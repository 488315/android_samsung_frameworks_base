package androidx.slice.builders;

import android.app.PendingIntent;
import android.graphics.drawable.Icon;
import androidx.core.graphics.drawable.IconCompat;
import androidx.slice.Slice;
import androidx.slice.core.SliceActionImpl;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SliceAction implements androidx.slice.core.SliceAction {
    public final SliceActionImpl mSliceAction;

    public SliceAction(PendingIntent pendingIntent, Icon icon, CharSequence charSequence) {
        this(pendingIntent, icon, 0, charSequence);
    }

    @Override // androidx.slice.core.SliceAction
    public final int getPriority() {
        return this.mSliceAction.mPriority;
    }

    @Override // androidx.slice.core.SliceAction
    public final boolean isToggle() {
        return this.mSliceAction.isToggle();
    }

    public final void setPrimaryAction(Slice.Builder builder) {
        SliceActionImpl sliceActionImpl = this.mSliceAction;
        PendingIntent pendingIntent = sliceActionImpl.mAction;
        if (pendingIntent == null) {
            pendingIntent = sliceActionImpl.mActionItem.getAction();
        }
        Slice.Builder buildSliceContent = sliceActionImpl.buildSliceContent(builder);
        buildSliceContent.addHints("shortcut", UniversalCredentialUtil.AGENT_TITLE);
        builder.addAction(pendingIntent, buildSliceContent.build(), sliceActionImpl.getSubtype());
    }

    public SliceAction(PendingIntent pendingIntent, Icon icon, int i, CharSequence charSequence) {
        this(pendingIntent, IconCompat.createFromIcon(icon), i, charSequence);
    }

    public SliceAction(PendingIntent pendingIntent, Icon icon, CharSequence charSequence, boolean z) {
        this(pendingIntent, IconCompat.createFromIcon(icon), charSequence, z);
    }

    public SliceAction(PendingIntent pendingIntent, IconCompat iconCompat, CharSequence charSequence) {
        this(pendingIntent, iconCompat, 0, charSequence);
    }

    public SliceAction(PendingIntent pendingIntent, IconCompat iconCompat, int i, CharSequence charSequence) {
        this.mSliceAction = new SliceActionImpl(pendingIntent, iconCompat, i, charSequence);
    }

    public SliceAction(PendingIntent pendingIntent, IconCompat iconCompat, CharSequence charSequence, boolean z) {
        this.mSliceAction = new SliceActionImpl(pendingIntent, iconCompat, charSequence, z);
    }

    public SliceAction(PendingIntent pendingIntent, CharSequence charSequence, boolean z) {
        this.mSliceAction = new SliceActionImpl(pendingIntent, charSequence, z);
    }

    public SliceAction(PendingIntent pendingIntent, CharSequence charSequence, long j, boolean z) {
        this.mSliceAction = new SliceActionImpl(pendingIntent, charSequence, j, z);
    }
}
