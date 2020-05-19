package com.wb.opengl;

import android.app.AppOpsManager;
import android.content.Context;
import android.os.Build;
import android.os.Process;

/**
 * @Author WangBu
 * @DATE 20-5-19 15:48
 * E-Mail Address：android_wb@163.com
 */
 class PermisssionUtils {

    protected static boolean checkOpsPermission(Context context, String permission) {
        // 6.0 api 23
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {

                AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
                String appName = AppOpsManager.permissionToOp(permission);
                if (null == appName) {
                    return true;
                }
                int apsMode = appOpsManager.checkOpNoThrow(appName, Process.myUid(), context.getPackageName());
                return apsMode == AppOpsManager.MODE_ALLOWED;
            } catch (Exception e) {
                e.printStackTrace();
                return true;
            }
        }
        return true;
    }
}
