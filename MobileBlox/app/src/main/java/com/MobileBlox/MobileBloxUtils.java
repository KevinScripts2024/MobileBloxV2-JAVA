package com.MobileBlox;

import android.content.ClipboardManager;
import android.content.ClipData;
import android.content.Context;
import android.provider.Settings;
import android.util.Log;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class MobileBloxUtils {

    static class ClipSetter implements Runnable {
        final String text;
        private final Context context;

        ClipSetter(Context context, String text) {
            this.context = context;
            this.text = text;
        }

        @Override
        public void run() {
            ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setPrimaryClip(ClipData.newPlainText("MobileBlox", this.text));
        }
    }

    static class ClipGetter implements Callable<String> {
        private final Context context;

        ClipGetter(Context context) {
            this.context = context;
        }

        @Override
        public String call() {
            ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            if (clipboard.hasPrimaryClip() && clipboard.getPrimaryClipDescription().hasMimeType("text/plain")) {
                return clipboard.getPrimaryClip().getItemAt(0).getText().toString();
            }
            return "";
        }
    }

    public static void setClipboardData(Context context, String data) {
        try {
            context.runOnUiThread(new ClipSetter(context, data));
        } catch (Exception e) {
            Log.e("MobileBlox", "Exception while setting clipboard data", e);
        }
    }

    public static String getClipboardData(Context context) {
        FutureTask<String> result = new FutureTask<>(new ClipGetter(context));
        context.runOnUiThread(result);

        try {
            return result.get();
        } catch (ExecutionException | InterruptedException e) {
            Log.e("MobileBlox", "Exception while getting clipboard data", e);
            return "";
        }
    }

    public static String getHWID(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }
}
