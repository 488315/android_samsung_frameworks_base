package android.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.internal.R;

@Deprecated
/* loaded from: classes5.dex */
public class AppSecurityPermissions {
    public static View getPermissionItemView(Context context, CharSequence charSequence, CharSequence charSequence2, boolean z) {
        return getPermissionItemViewOld(context, (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE), charSequence, charSequence2, z, context.getDrawable(z ? R.drawable.ic_bullet_key_permission : R.drawable.ic_text_dot));
    }

    private static View getPermissionItemViewOld(Context context, LayoutInflater layoutInflater, CharSequence charSequence, CharSequence charSequence2, boolean z, Drawable drawable) {
        View viewInflate = layoutInflater.inflate(R.layout.app_permission_item_old, (ViewGroup) null);
        TextView textView = (TextView) viewInflate.findViewById(R.id.permission_group);
        TextView textView2 = (TextView) viewInflate.findViewById(R.id.permission_list);
        ((ImageView) viewInflate.findViewById(R.id.perm_icon)).lambda$setImageURIAsync$2(drawable);
        if (charSequence != null) {
            textView.lambda$setTextAsync$0(charSequence);
            textView2.lambda$setTextAsync$0(charSequence2);
            return viewInflate;
        }
        textView.lambda$setTextAsync$0(charSequence2);
        textView2.setVisibility(8);
        return viewInflate;
    }
}
