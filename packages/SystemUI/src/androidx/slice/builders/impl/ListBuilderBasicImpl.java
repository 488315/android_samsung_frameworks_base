package androidx.slice.builders.impl;

import androidx.core.graphics.drawable.IconCompat;
import androidx.slice.Slice;
import androidx.slice.SliceItem;
import androidx.slice.SliceSpec;
import androidx.slice.SystemClock;
import androidx.slice.builders.ListBuilder;
import androidx.slice.builders.SliceAction;
import androidx.slice.core.SliceActionImpl;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import java.time.Duration;

/* loaded from: classes.dex */
public class ListBuilderBasicImpl extends TemplateBuilderImpl implements ListBuilder {
    public IconCompat mIconCompat;
    public SliceAction mSliceAction;
    public CharSequence mTitle;

    public ListBuilderBasicImpl(Slice.Builder builder, SliceSpec sliceSpec) {
        super(builder, sliceSpec);
    }

    @Override // androidx.slice.builders.impl.ListBuilder
    public final void addRow(ListBuilder.RowBuilder rowBuilder) {
        SliceAction sliceAction;
        CharSequence charSequence;
        if (this.mTitle == null && (charSequence = rowBuilder.mTitle) != null) {
            this.mTitle = charSequence;
        }
        if (this.mSliceAction != null || (sliceAction = rowBuilder.mPrimaryAction) == null) {
            return;
        }
        this.mSliceAction = sliceAction;
    }

    @Override // androidx.slice.builders.impl.TemplateBuilderImpl
    public final void apply(Slice.Builder builder) {
        IconCompat iconCompat;
        CharSequence charSequence;
        Slice.Builder builder2 = new Slice.Builder(this.mSliceBuilder);
        SliceAction sliceAction = this.mSliceAction;
        if (sliceAction != null) {
            CharSequence charSequence2 = this.mTitle;
            SliceActionImpl sliceActionImpl = sliceAction.mSliceAction;
            if (charSequence2 == null && (charSequence = sliceActionImpl.mTitle) != null) {
                this.mTitle = charSequence;
            }
            if (this.mIconCompat == null && (iconCompat = sliceActionImpl.mIcon) != null) {
                this.mIconCompat = iconCompat;
            }
            sliceAction.setPrimaryAction(builder2);
        }
        CharSequence charSequence3 = this.mTitle;
        if (charSequence3 != null) {
            builder2.addItem(new SliceItem(charSequence3, "text", (String) null, new String[]{UniversalCredentialUtil.AGENT_TITLE}));
        }
        IconCompat iconCompat2 = this.mIconCompat;
        if (iconCompat2 != null) {
            builder.addIcon(iconCompat2, null, UniversalCredentialUtil.AGENT_TITLE);
        }
        Slice sliceBuild = builder2.build();
        builder.getClass();
        builder.addSubSlice(sliceBuild, null);
    }

    @Override // androidx.slice.builders.impl.ListBuilder
    public final void setHeader(ListBuilder.HeaderBuilder headerBuilder) {
        CharSequence charSequence = headerBuilder.mTitle;
        if (charSequence != null) {
            this.mTitle = charSequence;
        }
    }

    @Override // androidx.slice.builders.impl.ListBuilder
    public final void setTtl(long j) {
        long jCurrentTimeMillis = -1;
        if (j != -1) {
            ((SystemClock) this.mClock).getClass();
            jCurrentTimeMillis = System.currentTimeMillis() + j;
        }
        this.mSliceBuilder.addTimestamp(jCurrentTimeMillis, "millis", "ttl");
    }

    @Override // androidx.slice.builders.impl.ListBuilder
    public final void setTtl(Duration duration) {
        setTtl(duration == null ? -1L : duration.toMillis());
    }
}
