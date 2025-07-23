package androidx.slice.builders;

import android.content.Context;
import android.net.Uri;
import androidx.collection.ArraySet;
import androidx.core.graphics.drawable.IconCompat;
import androidx.core.util.Pair;
import androidx.slice.Slice;
import androidx.slice.SliceProvider;
import androidx.slice.SliceSpec;
import androidx.slice.SliceSpecs;
import androidx.slice.SystemClock;
import androidx.slice.builders.impl.ListBuilderBasicImpl;
import androidx.slice.builders.impl.ListBuilderImpl;
import androidx.slice.builders.impl.TemplateBuilderImpl;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class ListBuilder extends TemplateSliceBuilder {
    public androidx.slice.builders.impl.ListBuilder mImpl;

    public ListBuilder(Context context, Uri uri, long j) {
        super(context, uri);
        this.mImpl.setTtl(j);
    }

    @Override // androidx.slice.builders.TemplateSliceBuilder
    public final TemplateBuilderImpl selectImpl() {
        SliceSpec sliceSpec = SliceSpecs.LIST_V2;
        boolean checkCompatible = checkCompatible(sliceSpec);
        Slice.Builder builder = this.mBuilder;
        if (checkCompatible) {
            ArraySet arraySet = SliceProvider.sSpecs;
            return new ListBuilderImpl(builder, sliceSpec, new SystemClock());
        }
        SliceSpec sliceSpec2 = SliceSpecs.LIST;
        if (checkCompatible(sliceSpec2)) {
            ArraySet arraySet2 = SliceProvider.sSpecs;
            return new ListBuilderImpl(builder, sliceSpec2, new SystemClock());
        }
        SliceSpec sliceSpec3 = SliceSpecs.BASIC;
        if (checkCompatible(sliceSpec3)) {
            return new ListBuilderBasicImpl(builder, sliceSpec3);
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // androidx.slice.builders.TemplateSliceBuilder
    public final void setImpl(TemplateBuilderImpl templateBuilderImpl) {
        this.mImpl = (androidx.slice.builders.impl.ListBuilder) templateBuilderImpl;
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class HeaderBuilder {
        public CharSequence mTitle;
        public final Uri mUri;

        public HeaderBuilder() {
            this.mUri = null;
        }

        public HeaderBuilder(Uri uri) {
            this.mUri = uri;
        }
    }

    public ListBuilder(Context context, Uri uri, Duration duration) {
        super(context, uri);
        this.mImpl.setTtl(duration);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class RowBuilder {
        public CharSequence mContentDescription;
        public final List mEndItems;
        public final List mEndLoads;
        public final List mEndTypes;
        public SliceAction mPrimaryAction;
        public CharSequence mTitle;
        public final Uri mUri;

        public RowBuilder() {
            this.mEndItems = new ArrayList();
            this.mEndTypes = new ArrayList();
            this.mEndLoads = new ArrayList();
            this.mUri = null;
        }

        public final void addEndItem(IconCompat iconCompat) {
            ((ArrayList) this.mEndItems).add(new Pair(iconCompat, 0));
            ((ArrayList) this.mEndTypes).add(1);
            ((ArrayList) this.mEndLoads).add(Boolean.FALSE);
        }

        public RowBuilder(Uri uri) {
            this.mEndItems = new ArrayList();
            this.mEndTypes = new ArrayList();
            this.mEndLoads = new ArrayList();
            this.mUri = uri;
        }
    }
}
