package android.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.internal.C4337R;

@Deprecated
/* loaded from: classes4.dex */
public class AppSecurityPermissions {
  public static View getPermissionItemView(
      Context context, CharSequence grpName, CharSequence description, boolean dangerous) {
    LayoutInflater inflater =
        (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    Drawable icon =
        context.getDrawable(
            dangerous ? C4337R.drawable.ic_bullet_key_permission : C4337R.drawable.ic_text_dot);
    return getPermissionItemViewOld(context, inflater, grpName, description, dangerous, icon);
  }

  private static View getPermissionItemViewOld(
      Context context,
      LayoutInflater inflater,
      CharSequence grpName,
      CharSequence permList,
      boolean dangerous,
      Drawable icon) {
    View permView = inflater.inflate(C4337R.layout.app_permission_item_old, (ViewGroup) null);
    TextView permGrpView = (TextView) permView.findViewById(C4337R.id.permission_group);
    TextView permDescView = (TextView) permView.findViewById(C4337R.id.permission_list);
    ImageView imgView = (ImageView) permView.findViewById(C4337R.id.perm_icon);
    imgView.lambda$setImageURIAsync$2(icon);
    if (grpName != null) {
      permGrpView.setText(grpName);
      permDescView.setText(permList);
    } else {
      permGrpView.setText(permList);
      permDescView.setVisibility(8);
    }
    return permView;
  }
}
