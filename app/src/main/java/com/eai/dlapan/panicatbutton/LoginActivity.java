package com.eai.dlapan.panicatbutton;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private Button btnFB, btnGP, btnTW, btnEmail;
    private LinearLayout layoutSocial, layoutEmail;
    private boolean bySocial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnFB=(Button)findViewById(R.id.btnLoginFB);
        btnGP=(Button)findViewById(R.id.btnLoginGP);
        btnTW=(Button)findViewById(R.id.btnLoginTW);
        btnEmail=(Button)findViewById(R.id.btnLoginEmail);
        btnTrigger(btnFB, 0);
        btnTrigger(btnGP, 1);
        btnTrigger(btnTW, 2);
        btnEmailAct(btnEmail);

        layoutSocial=findViewById(R.id.groupInputSocial);
        layoutEmail=findViewById(R.id.groupInputManual);

        bySocial=true;
    }

    /*
    * Login Action
    * */
    private void btnTrigger(Button button, final int id){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAction(id);
            }
        });
    }
    private void btnAction(int id){
        //params[0: FB, 1:GP, 2: TW]
        String[] btns = new String[]{"Facebook","Google","Twitter"};
        Toast.makeText(this, "Login with "+btns[id], Toast.LENGTH_SHORT).show();
    }
    private void btnEmailAct(final Button btn){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bySocial){
                    layoutSocial.setVisibility(View.GONE);
                    layoutEmail.setVisibility(View.VISIBLE);
                    btn.setText(getResources().getString(R.string.login_btn_mode_social));
                    bySocial=false;
                }else{
                    layoutSocial.setVisibility(View.VISIBLE);
                    layoutEmail.setVisibility(View.GONE);
                    btn.setText(getResources().getString(R.string.login_btn_mode_manual));
                    bySocial=true;
                }
            }
        });
    }
}
