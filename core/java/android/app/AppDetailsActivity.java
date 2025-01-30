package android.app;

import android.content.Intent;
import android.net.Uri;
import android.p009os.Bundle;
import android.provider.Settings;

/* loaded from: classes.dex */
public class AppDetailsActivity extends Activity {
  @Override // android.app.Activity
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
    intent.setData(Uri.fromParts("package", getPackageName(), null));
    startActivity(intent);
    finish();
  }
}
