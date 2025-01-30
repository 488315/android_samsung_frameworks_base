package com.android.internal.accessibility.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.internal.C4337R;
import java.util.List;

/* loaded from: classes4.dex */
class ButtonTargetAdapter extends TargetAdapter {
  private List<AccessibilityTarget> mTargets;

  ButtonTargetAdapter(List<AccessibilityTarget> targets) {
    this.mTargets = targets;
  }

  @Override // android.widget.Adapter
  public int getCount() {
    return this.mTargets.size();
  }

  @Override // android.widget.Adapter
  public Object getItem(int position) {
    return this.mTargets.get(position);
  }

  @Override // android.widget.Adapter
  public long getItemId(int position) {
    return position;
  }

  @Override // android.widget.Adapter
  public View getView(int position, View convertView, ViewGroup parent) {
    Context context = parent.getContext();
    View root =
        LayoutInflater.from(context)
            .inflate(C4337R.layout.accessibility_button_chooser_item, parent, false);
    AccessibilityTarget target = this.mTargets.get(position);
    ImageView iconView = (ImageView) root.findViewById(C4337R.id.accessibility_button_target_icon);
    TextView labelView = (TextView) root.findViewById(C4337R.id.accessibility_button_target_label);
    iconView.lambda$setImageURIAsync$2(target.getIcon());
    labelView.setText(target.getLabel());
    return root;
  }
}
