package com.android.systemui;

import android.R;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.IconDrawableFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.android.internal.app.AlertActivity;
import com.android.internal.app.AlertController;
import com.android.internal.logging.MetricsLogger;
import java.util.ArrayList;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ForegroundServicesDialog extends AlertActivity implements AdapterView.OnItemSelectedListener, DialogInterface.OnClickListener, AlertController.AlertParams.OnPrepareListViewListener {
    public PackageItemAdapter mAdapter;
    public final AnonymousClass1 mAppClickListener = new DialogInterface.OnClickListener() { // from class: com.android.systemui.ForegroundServicesDialog.1
        @Override // android.content.DialogInterface.OnClickListener
        public final void onClick(DialogInterface dialogInterface, int i) {
            String str = ((ApplicationInfo) ForegroundServicesDialog.this.mAdapter.getItem(i)).packageName;
            Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.fromParts("package", str, null));
            ForegroundServicesDialog.this.startActivity(intent);
            ForegroundServicesDialog.this.finish();
        }
    };
    public LayoutInflater mInflater;
    public final MetricsLogger mMetricsLogger;
    public String[] mPackages;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class PackageItemAdapter extends ArrayAdapter {
        public final IconDrawableFactory mIconDrawableFactory;
        public final LayoutInflater mInflater;
        public final PackageManager mPm;

        public PackageItemAdapter(Context context) {
            super(context, R.layout.foreground_service_item);
            this.mPm = context.getPackageManager();
            this.mInflater = LayoutInflater.from(context);
            this.mIconDrawableFactory = IconDrawableFactory.newInstance(context, true);
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public final View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = this.mInflater.inflate(R.layout.foreground_service_item, viewGroup, false);
            }
            ((ImageView) view.findViewById(R.id.app_icon)).setImageDrawable(this.mIconDrawableFactory.getBadgedIcon((ApplicationInfo) getItem(i)));
            ((TextView) view.findViewById(R.id.app_name)).setText(((ApplicationInfo) getItem(i)).loadLabel(this.mPm));
            return view;
        }
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.systemui.ForegroundServicesDialog$1] */
    public ForegroundServicesDialog(MetricsLogger metricsLogger) {
        this.mMetricsLogger = metricsLogger;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        finish();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mInflater = LayoutInflater.from(this);
        PackageItemAdapter packageItemAdapter = new PackageItemAdapter(this);
        this.mAdapter = packageItemAdapter;
        AlertController.AlertParams alertParams = ((AlertActivity) this).mAlertParams;
        alertParams.mAdapter = packageItemAdapter;
        alertParams.mOnClickListener = this.mAppClickListener;
        alertParams.mCustomTitleView = this.mInflater.inflate(R.layout.foreground_service_title, (ViewGroup) null);
        alertParams.mIsSingleChoice = true;
        alertParams.mOnItemSelectedListener = this;
        alertParams.mPositiveButtonText = getString(R.string.immersive_cling_title);
        alertParams.mPositiveButtonListener = this;
        alertParams.mOnPrepareListViewListener = this;
        updateApps(getIntent());
        if (this.mPackages != null) {
            setupAlert();
        } else {
            Log.w("ForegroundServicesDialog", "No packages supplied");
            finish();
        }
    }

    public final void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        updateApps(intent);
    }

    public final void onPause() {
        super.onPause();
        this.mMetricsLogger.hidden(944);
    }

    public final void onResume() {
        super.onResume();
        this.mMetricsLogger.visible(944);
    }

    public final void onStop() {
        super.onStop();
        if (isChangingConfigurations()) {
            return;
        }
        finish();
    }

    public final void updateApps(Intent intent) {
        String[] stringArrayExtra = intent.getStringArrayExtra("packages");
        this.mPackages = stringArrayExtra;
        if (stringArrayExtra != null) {
            PackageItemAdapter packageItemAdapter = this.mAdapter;
            packageItemAdapter.clear();
            ArrayList arrayList = new ArrayList();
            for (String str : stringArrayExtra) {
                try {
                    arrayList.add(packageItemAdapter.mPm.getApplicationInfo(str, 4202496));
                } catch (PackageManager.NameNotFoundException unused) {
                }
            }
            arrayList.sort(new ApplicationInfo.DisplayNameComparator(packageItemAdapter.mPm));
            packageItemAdapter.addAll(arrayList);
        }
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public final void onNothingSelected(AdapterView adapterView) {
    }

    public final void onPrepareListView(ListView listView) {
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public final void onItemSelected(AdapterView adapterView, View view, int i, long j) {
    }
}
