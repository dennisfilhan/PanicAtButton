package com.eai.dlapan.panicatbutton.ext;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.eai.dlapan.panicatbutton.LoginActivity;
import com.eai.dlapan.panicatbutton.R;

import java.util.HashMap;
import java.util.Map;

import pl.bclogic.pulsator4droid.library.PulsatorLayout;

public class PanicDialog {
    private Activity activity;
    private PulsatorLayout pulsator;

    public PanicDialog(Activity activity) {
        this.activity = activity;
    }

    public PanicDialog(Activity activity, PulsatorLayout pulsatorLayout) {
        this.activity = activity;
        this.pulsator = pulsatorLayout;
    }

    public void show() {
        Dialog dialog = new Dialog(this.activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.item_panic_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCanceledOnTouchOutside(true);
        this.btnClicked(dialog);
        dialog.show();
    }

    private void btnClicked(Dialog dialog) {
        Button btnH, btnP, btnF, btnA;
        btnH = (Button) dialog.findViewById(R.id.btnDialogPanicHH);
        btnP = (Button) dialog.findViewById(R.id.btnDialogPanicPP);
        btnF = (Button) dialog.findViewById(R.id.btnDialogPanicFF);
        btnA = (Button) dialog.findViewById(R.id.btnDialogPanicAA);
        btnClick(dialog, btnH, "HOSPITAL");
        btnClick(dialog, btnP, "POLICE");
        btnClick(dialog, btnF, "FIREFIGHTER");
        btnClick(dialog, btnA, "UNKNOWN");
    }

    private void btnAction(Dialog dialog, String id) {
        String label = this.getEnumText(id);
        Log.d("PANIC_BUTTON", "Selected " + label);
        dialog.dismiss();
        Toast.makeText(this.activity, "Selected " + label, Toast.LENGTH_SHORT).show();
        if(this.pulsator!=null){
            this.pulsator.setDuration(1000);
            pulsator.setDuration(pulsator.getDuration());
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                activity.startActivity(new Intent(activity.getApplicationContext(), LoginActivity.class));
            }
        }, 1000*8);
    }

    private void btnClick(final Dialog dialog, Button btn, final String id) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAction(dialog, id);
            }
        });
    }

    private String getEnumText(String id){
        Map<String, String> map = new HashMap<>();
        map.put("HOSPITAL","Rumah Sakit");
        map.put("POLICE","Polisi");
        map.put("FIREFIGHTER","Pemadam Kebakaran");
        map.put("UNKNOWN","Belum Ditentukan");

        return map.get(id);
    }
}
