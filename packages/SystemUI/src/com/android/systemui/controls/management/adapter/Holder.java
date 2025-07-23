package com.android.systemui.controls.management.adapter;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.controls.management.model.MainModel;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class Holder extends RecyclerView.ViewHolder {

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class UpdateReq {
        public static final /* synthetic */ UpdateReq[] $VALUES;
        public static final UpdateReq UPDATE_DIM_STATUS;

        static {
            UpdateReq updateReq = new UpdateReq("UPDATE_DIM_STATUS", 0);
            UPDATE_DIM_STATUS = updateReq;
            UpdateReq[] updateReqArr = {updateReq};
            $VALUES = updateReqArr;
            EnumEntriesKt.enumEntries(updateReqArr);
        }

        private UpdateReq(String str, int i) {
        }

        public static UpdateReq valueOf(String str) {
            return (UpdateReq) Enum.valueOf(UpdateReq.class, str);
        }

        public static UpdateReq[] values() {
            return (UpdateReq[]) $VALUES.clone();
        }
    }

    public /* synthetic */ Holder(View view, DefaultConstructorMarker defaultConstructorMarker) {
        this(view);
    }

    public abstract void bindData(MainModel mainModel);

    private Holder(View view) {
        super(view);
    }
}
