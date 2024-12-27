package android.os;

import android.content.Context;

public interface IServiceCreator {
    IBinder createService(Context context);
}
