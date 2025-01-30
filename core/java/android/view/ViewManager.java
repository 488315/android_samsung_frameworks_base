package android.view;


/* loaded from: classes4.dex */
public interface ViewManager {
  void addView(View view, ViewGroup.LayoutParams layoutParams);

  void removeView(View view);

  void updateViewLayout(View view, ViewGroup.LayoutParams layoutParams);
}
