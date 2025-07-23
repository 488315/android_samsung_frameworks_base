package android.view;

import android.view.inputmethod.Flags;
import com.android.internal.protolog.ProtoLogGroup;

/* loaded from: classes4.dex */
final class ViewProtoLogGroups {
    static final ProtoLogGroup[] ALL_GROUPS;
    static final ProtoLogGroup IME_INSETS_CONTROLLER;

    ViewProtoLogGroups() {
    }

    static {
        ProtoLogGroup protoLogGroup = new ProtoLogGroup("IME_INSETS_CONTROLLER", "InsetsController", Flags.refactorInsetsController());
        IME_INSETS_CONTROLLER = protoLogGroup;
        ALL_GROUPS = new ProtoLogGroup[]{protoLogGroup};
    }
}
