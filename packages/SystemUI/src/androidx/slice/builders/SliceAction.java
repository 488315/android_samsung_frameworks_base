package androidx.slice.builders;

import android.app.PendingIntent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Icon;
import androidx.core.graphics.drawable.IconCompat;
import androidx.slice.Slice;
import androidx.slice.core.SliceActionImpl;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class SliceAction implements androidx.slice.core.SliceAction {
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

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public SliceAction(PendingIntent pendingIntent, Icon icon, int i, CharSequence charSequence) {
        this(pendingIntent, IconCompat.Api23Impl.createFromIconInner(icon), i, charSequence);
        PorterDuff.Mode mode = IconCompat.DEFAULT_TINT_MODE;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public SliceAction(PendingIntent pendingIntent, Icon icon, CharSequence charSequence, boolean z) {
        this(pendingIntent, IconCompat.Api23Impl.createFromIconInner(icon), charSequence, z);
        PorterDuff.Mode mode = IconCompat.DEFAULT_TINT_MODE;
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
