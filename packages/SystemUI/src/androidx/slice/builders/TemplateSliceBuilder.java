package androidx.slice.builders;

import android.content.Context;
import android.net.Uri;
import androidx.collection.ArraySet;
import androidx.slice.Slice;
import androidx.slice.SliceManagerWrapper;
import androidx.slice.SliceProvider;
import androidx.slice.SliceSpec;
import androidx.slice.builders.impl.TemplateBuilderImpl;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class TemplateSliceBuilder {
    public final Slice.Builder mBuilder;
    public final List mSpecs;

    public TemplateSliceBuilder(TemplateBuilderImpl templateBuilderImpl) {
        this.mBuilder = null;
        setImpl(templateBuilderImpl);
    }

    public final boolean checkCompatible(SliceSpec sliceSpec) {
        int size = this.mSpecs.size();
        for (int i = 0; i < size; i++) {
            SliceSpec sliceSpec2 = (SliceSpec) this.mSpecs.get(i);
            if (sliceSpec2.mType.equals(sliceSpec.mType) && sliceSpec2.mRevision >= sliceSpec.mRevision) {
                return true;
            }
        }
        return false;
    }

    public TemplateBuilderImpl selectImpl() {
        return null;
    }

    public abstract void setImpl(TemplateBuilderImpl templateBuilderImpl);

    public TemplateSliceBuilder(Context context, Uri uri) {
        ArrayList arrayList;
        this.mBuilder = new Slice.Builder(uri);
        if (SliceProvider.sSpecs != null) {
            arrayList = new ArrayList(SliceProvider.sSpecs);
        } else {
            Set<android.app.slice.SliceSpec> pinnedSpecs = new SliceManagerWrapper(context).mManager.getPinnedSpecs(uri);
            ArraySet arraySet = new ArraySet();
            if (pinnedSpecs != null) {
                Iterator<android.app.slice.SliceSpec> it = pinnedSpecs.iterator();
                while (it.hasNext()) {
                    android.app.slice.SliceSpec next = it.next();
                    arraySet.add(next == null ? null : new SliceSpec(next.getType(), next.getRevision()));
                }
            }
            arrayList = new ArrayList(arraySet);
        }
        this.mSpecs = arrayList;
        TemplateBuilderImpl selectImpl = selectImpl();
        if (selectImpl != null) {
            setImpl(selectImpl);
            return;
        }
        throw new IllegalArgumentException("No valid specs found");
    }
}
